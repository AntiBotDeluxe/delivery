package de.antibotdeluxe.delivery.client;

import de.antibotdeluxe.delivery.codec.DeliveryByteDecoder;
import de.antibotdeluxe.delivery.codec.DeliveryByteEncoder;
import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import de.antibotdeluxe.delivery.codec.DeliveryPacketVault;
import de.antibotdeluxe.delivery.event.DeliveryEventManager;
import de.antibotdeluxe.delivery.misc.exceptions.AlreadyConnectedException;
import de.antibotdeluxe.delivery.misc.exceptions.ConnectionFailedException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class DeliveryClient {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private static final Logger LOGGER = Logger.getLogger("[Delivery]");

    private final DeliveryEventManager eventManager;
    private final DeliveryPacketVault packetVault;

    private final EventLoopGroup masterGroup;
    private Channel channel;

    public DeliveryClient() {
        this(new DeliveryPacketVault());
    }

    /**
     * Initialize the {@link DeliveryClient}
     *
     * @param vault
     *          {@link DeliveryPacketVault where all {@link DeliveryPacket}'s are stored.
     */
    public DeliveryClient(DeliveryPacketVault vault) {
        this.eventManager = new DeliveryEventManager();
        this.packetVault = vault;
        this.masterGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
    }

    /**
     * Connects the {@link DeliveryClient} to a {@link de.antibotdeluxe.delivery.server.DeliveryServer}
     *
     * @param host
     *          {@link de.antibotdeluxe.delivery.server.DeliveryServer} host address
     * @param port
     *          {@link de.antibotdeluxe.delivery.server.DeliveryServer} host port
     */
    public void connectClient(String host, int port) {
        LOGGER.log(Level.SEVERE, () -> "Connecting to " + host + ":" + port + " ...");
        if (this.channel != null)
            throw new AlreadyConnectedException(this.channel);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(masterGroup)
                    .channel((Epoll.isAvailable()) ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new DeliveryByteDecoder(getPacketVault()));
                            channel.pipeline().addLast(new DeliveryByteEncoder(getPacketVault()));
                            channel.pipeline().addLast(new DeliveryClientNetworkAdapter(DeliveryClient.this));
                        }
                    });
            this.channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ConnectionFailedException(host, port);
        }
        LOGGER.log(Level.SEVERE, () -> "Connection established");
    }

    /**
     * Disconnects the {@link DeliveryClient} from the {@link de.antibotdeluxe.delivery.server.DeliveryServer}.
     */
    public void disconnectClient() {
        LOGGER.log(Level.SEVERE, () -> "Closing connection...");
        this.channel.disconnect();
        this.channel.close();
        this.masterGroup.shutdownGracefully();
        LOGGER.log(Level.SEVERE, () -> "Successfully closed connection!");
    }

    /**
     * Sends a {@link DeliveryPacket} to the {@link de.antibotdeluxe.delivery.server.DeliveryServer}
     *
     * @param deliveryPacket
     *          {@link DeliveryPacket} which should be sent
     */
    public void sendDeliveryPacket(DeliveryPacket deliveryPacket) {
        if (deliveryPacket == null)
            throw new NullPointerException();
        getChannel().writeAndFlush(deliveryPacket);
    }

    /**
     * Returns the {@link de.antibotdeluxe.delivery.server.DeliveryServer} host address.
     *
     * @return
     *      DeliveryServer host address
     */
    public String getHostAddress() {
        return this.channel.remoteAddress().toString().replace("/", "").split(":")[0];
    }

    /**
     * Class will be used to listen for incoming traffic and accept connections.
     */

    static class DeliveryClientNetworkAdapter extends SimpleChannelInboundHandler<DeliveryPacket> {

        private final DeliveryClient client;

        DeliveryClientNetworkAdapter(DeliveryClient client) {
            this.client = client;
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
            this.client.getEventManager().callHandlers(msg, ctx);
        }

    }

    /**
     * Returns the {@link DeliveryPacketVault} containing all the registered {@link DeliveryPacket}'s
     *
     * @return DeliveryPacketVault
     */
    public DeliveryPacketVault getPacketVault() {
        return this.packetVault;
    }

    /**
     * Returns the {@link DeliveryEventManager} containing all the {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}'s
     *
     * @return DeliveryEventManager
     */
    public DeliveryEventManager getEventManager() {
        return this.eventManager;
    }

    /**
     * Return the {@link EventLoopGroup}
     *
     * @return EventLoopGroup
     */
    public EventLoopGroup getMasterGroup() {
        return this.masterGroup;
    }

    /**
     * Returns the {@link Channel} with the current active connection.
     *
     * @return Channel
     */
    public Channel getChannel() {
        return this.channel;
    }
}
