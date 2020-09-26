package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class DeliveryEventManager {

    private final Map<Class<? extends DeliveryPacket>, DeliveryEventHandlerList> localEventVault = new HashMap<>();
    private final DeliveryEventHandlerList globalEventVault = new DeliveryEventHandlerList();

    public void addHandler(DeliveryEventHandler handler) {
        Class<? extends DeliveryPacket> packetType = handler.getPacketType();
        if (packetType.equals(DeliveryPacket.class)) {
            this.globalEventVault.addHandler(handler);
            return;
        }
        this.localEventVault.computeIfAbsent(packetType, k -> new DeliveryEventHandlerList());
        this.localEventVault.get(packetType).addHandler(handler);
    }

    public void addHandlers(DeliveryEventHandler... handlers) {
        for (DeliveryEventHandler handler : handlers) {
            this.addHandler(handler);
        }
    }

    public void callHandlers(DeliveryPacket packet, ChannelHandlerContext ctx) {
        this.globalEventVault.callHandlers(packet, ctx);
        this.localEventVault.computeIfPresent(packet.getClass(), (k, v) -> {
            v.callHandlers(packet, ctx);
            return v;
        });
    }

}
