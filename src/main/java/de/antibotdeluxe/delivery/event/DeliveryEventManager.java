package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

/**
 * Used by the server and the client to automatically call stored code in the {@link DeliveryEventHandler}'s or bind new ones to the
 * {@link DeliveryEventHandlerList} for each specific {@link DeliveryPacket}.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryEventManager {

    private final HashMap<Class<? extends DeliveryPacket>, DeliveryEventHandlerList> localEventVault = new HashMap<>();
    private final DeliveryEventHandlerList globalEventVault = new DeliveryEventHandlerList();

    /**
     * Binds a new {@link DeliveryEventHandler} to the system.<br>
     * <p>
     * <strong>Note</strong><br>
     * If the capture of the {@link DeliveryPacket} is <code>DeliverPacket.class</code> then the {@link DeliveryEventHandler} will be
     * bound to the global registry
     *
     * @param handler
     *          {@link DeliveryEventHandler} which should be bound to the system
     */
    public void bindHandlers(DeliveryEventHandler handler) {
        Class<? extends DeliveryPacket> packetCapture = handler.getPacketCapture();
        if (packetCapture.equals(DeliveryPacket.class)) {
            this.globalEventVault.addHandler(handler);
            return;
        }
        this.localEventVault.computeIfAbsent(packetCapture, k -> new DeliveryEventHandlerList());
        this.localEventVault.get(packetCapture).addHandler(handler);
    }

    /**
     * Calls each {@link DeliveryEventHandler} in the {@link DeliveryEventHandlerList} matching the type of the {@link DeliveryPacket}.
     *
     * @param packet
     *          {@link DeliveryPacket} parsed by the {@link de.antibotdeluxe.delivery.codec.DeliveryByteDecoder}
     * @param ctx
     *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     */
    public void callHandlers(DeliveryPacket packet, ChannelHandlerContext ctx) {
        this.globalEventVault.callHandlers(packet, ctx);
        this.localEventVault.computeIfPresent(packet.getClass(), (k, v) -> {
            v.callHandlers(packet, ctx);
            return v;
        });
    }

}
