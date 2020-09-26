package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import de.antibotdeluxe.delivery.misc.exceptions.UUIDAlreadyBoundException;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;

public class DeliveryEventHandlerList {

    private final Map<DeliveryEventPriority, List<DeliveryEventHandler>> storedHandlers = new EnumMap<>(DeliveryEventPriority.class);

    private Class<? extends DeliveryPacket> packetType;

    public DeliveryEventHandlerList() {
        this.storedHandlers.put(DeliveryEventPriority.LOW, new ArrayList<>());
        this.storedHandlers.put(DeliveryEventPriority.MEDIUM, new ArrayList<>());
        this.storedHandlers.put(DeliveryEventPriority.HIGH, new ArrayList<>());
    }

    public void addHandler(DeliveryEventHandler handler) {
        if (this.packetType == null) this.packetType = handler.getPacketType();
        this.storedHandlers.values().forEach(v -> v.forEach(storedHandler -> {
            if (storedHandler.equals(handler))
                throw new UUIDAlreadyBoundException(handler.getUuid());
        }));
        this.storedHandlers.get(handler.getPriority()).add(handler);
    }

    public void callHandlers(DeliveryPacket packet, ChannelHandlerContext ctx) {
        this.storedHandlers.get(DeliveryEventPriority.HIGH).forEach(handler -> handler.call(packet, ctx));
        this.storedHandlers.get(DeliveryEventPriority.MEDIUM).forEach(handler -> handler.call(packet, ctx));
        this.storedHandlers.get(DeliveryEventPriority.LOW).forEach(handler -> handler.call(packet, ctx));
    }

    public Class<? extends DeliveryPacket> getPacketType() {
        return this.packetType;
    }
}
