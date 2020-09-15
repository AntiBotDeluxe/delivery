package de.antibotdeluxe.delivery.server;

import de.antibotdeluxe.delivery.codec.DeliveryByteDecoder;
import de.antibotdeluxe.delivery.codec.DeliveryByteEncoder;
import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import de.antibotdeluxe.delivery.codec.DeliveryPacketVault;
import de.antibotdeluxe.delivery.event.DeliveryEventManager;
import de.antibotdeluxe.delivery.misc.Utility;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class is the heart of the application.<br>
 * It boots up and initializes<a target="_blank" href="http://netty.io">Netty.io</a> so new connections can be accepted.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryServer {


    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private static final Logger LOGGER = Logger.getLogger("[Delivery-Server]");

    private final int port;

    private final ChannelGroup channelGroup;
    private final EventLoopGroup slaveGroup;
    private final EventLoopGroup masterGroup;

    private final DeliveryPacketVault packetVault;
    private final DeliveryEventManager eventManager;

    private ServerSocket serverSocket;

    /**
     * Initializes the {@link DeliveryServer}.
     *
     * @param port
     *          port the DeliveryServer will be trying to reserve until it's started
     */
    public DeliveryServer(int port) {
        this(port, new DeliveryPacketVault());
    }

    /**
     * Initializes the {@link DeliveryServer}.
     *
     * @param port
     *          port The {@link DeliveryServer} will be trying to reserve until it's started
     * @param vault
     *          vault The {@link DeliveryServer} will try to communicate with the {@link DeliveryPacket}'s containing in this
     *          {@link DeliveryPacketVault}
     */
    public DeliveryServer(int port, DeliveryPacketVault vault) {
        Utility.checkPortAvailability(port);
        this.port = port;
        try { this.serverSocket = new ServerSocket(port); } catch (IOException exception) { exception.printStackTrace(); }
        this.packetVault = vault;
        this.eventManager = new DeliveryEventManager();
        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.slaveGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.masterGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
    }

    /**
     * Start up the {@link DeliveryServer} and accept connections.
     */
    public void runServer() {
        LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Starting up the server...");
        try {
            this.serverSocket.close();
            new ServerBootstrap()
                    .group(masterGroup, masterGroup)
                    .channel((Epoll.isAvailable()) ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new DeliveryByteDecoder(packetVault));
                            channel.pipeline().addLast(new DeliveryByteEncoder(packetVault));
                            channel.pipeline().addLast(new DeliveryServerNetworkWorkAdapter(DeliveryServer.this));
                        }
                    }).bind(this.port).sync();
            LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Server is running fine and is now accepting connections!");
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] An error occurred while starting the server.");
            exception.printStackTrace();
        }
    }

    /**
     * Shutdown the {@link DeliveryServer} gracefully by dropping all connections.
     */
    public void endServer() {
        LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Shutting down the server...");
        LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Disconnecting Channels..");
        this.channelGroup.close();
        LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Closing EventLoopGroup..");
        this.slaveGroup.shutdownGracefully();
        this.masterGroup.shutdownGracefully();
        LOGGER.log(Level.SEVERE, () -> "[Delivery-Server] Stopping server...");
    }

    /**
     * Broadcasts a {@link DeliveryPacket} to each currently connected {@link Channel}.
     *
     * @param deliveryPacket
     *          {@link DeliveryPacket} which should be sent
     */
    public void broadcastDeliveryPacket(DeliveryPacket deliveryPacket) {
        this.getChannelGroup().forEach(c -> c.writeAndFlush(deliveryPacket));
    }

    /**
     * Class will be used to listen for incoming traffic and accept connections.
     */

    static class DeliveryServerNetworkWorkAdapter extends SimpleChannelInboundHandler<DeliveryPacket> {

        private final DeliveryServer server;

        DeliveryServerNetworkWorkAdapter(DeliveryServer server) {
            this.server = server;
        }

        /**
         * Calls {@link DeliveryEventManager} which then calls the {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}'s for the
         * correct {@link DeliveryPacket}.
         *
         * @param ctx
         *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
         * @param msg
         *          {@link DeliveryPacket} provided by {@link DeliveryByteDecoder}
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DeliveryPacket msg) {
            this.server.getEventManager().callHandlers(msg, ctx);
        }

        /**
         * Adds a new {@link Channel} to the {@link ChannelGroup} if it connects successfully.
         *
         * @param ctx
         *          {@link ChannelHandlerContext provided by <a target="_blank" href="http://netty.io">Netty.io</a>}
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            this.server.getChannelGroup().add(ctx.channel());
        }

        /**
         * Drops an old {@link Channel} from the {@link ChannelGroup} if it has lost the connection or disconnected.
         *
         * @param ctx
         *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
         */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            this.server.getChannelGroup().remove(ctx.channel());
        }

    }

    /**
     * Returns the current amount of connected {@link Channel}'s connected to the {@link DeliveryServer}
     *
     * @return ChannelGroup size
     */
    public int getCurrentConnectionsCount() {
        return this.channelGroup.size();
    }

    /**
     * Returns the port which the {@link DeliveryServer} will accept connections on.
     *
     * @return port the {@link DeliveryServer} runs on
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Returns the {@link ChannelGroup} which includes all currently connected connections.
     *
     * @return ChannelGroup with current connections
     */
    public ChannelGroup getChannelGroup() {
        return this.channelGroup;
    }

    /**
     * Returns the slave {@link EventLoopGroup}.
     *
     * @return EventLoopGroup
     */
    public EventLoopGroup getSlaveGroup() {
        return this.slaveGroup;
    }

    /**
     * Returns the master {@link EventLoopGroup}.
     *
     * @return EventLoopGroup
     */
    public EventLoopGroup getMasterGroup() {
        return this.masterGroup;
    }

    /**
     * Returns the {@link DeliveryPacketVault} which includes all registered packets.
     * <p>
     * <strong>Note</strong><br>
     * The {@link DeliveryPacketVault} has to be equal with the one on the client side.
     *
     * @return DeliveryPacketVault containing all registered {@link DeliveryPacket}'s
     */
    public DeliveryPacketVault getPacketVault() {
        return this.packetVault;
    }

    /**
     * Returns the {@link DeliveryEventManager} which includes all {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}'s.
     *
     * @return DeliveryEventManager containing all registered {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}'s
     */
    public DeliveryEventManager getEventManager() {
        return this.eventManager;
    }
}
