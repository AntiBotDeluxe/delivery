package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public abstract class DeliveryEventHandler {

    public abstract void call(DeliveryPacket packet, ChannelHandlerContext ctx);

    private final UUID uuid;

    private DeliveryEventPriority priority;
    private Class<? extends DeliveryPacket> packetType;

    public DeliveryEventHandler() {
        this(UUID.randomUUID());
    }

    public DeliveryEventHandler(UUID uuid) {
        this.uuid = uuid;
        this.setPriority(DeliveryEventPriority.LOW);
    }

    public DeliveryEventHandler setPriority(DeliveryEventPriority priority) {
        this.priority = priority;
        return this;
    }

    public DeliveryEventPriority getPriority() {
        return this.priority;
    }

    public DeliveryEventHandler listenFor(Class<? extends DeliveryPacket> packetType) {
        this.packetType = packetType;
        return this;
    }

    public Class<? extends DeliveryPacket> getPacketType() {
        return this.packetType;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "DeliveryEventHandler-" + uuid.toString();
    }
}
