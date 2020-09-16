package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

/**
 * Abstract class is used to store the runtime code which should be fired if a specific packet was detected in the
 * {@link DeliveryEventManager}.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public abstract class DeliveryEventHandler {

    /**
     * Will be overwritten by each {@link DeliveryEventHandler} with the actual code which should be executed.
     *
     * @param packet
     *          {@link DeliveryPacket} which was decoded by the {@link de.antibotdeluxe.delivery.codec.DeliveryByteDecoder}
     * @param ctx
     *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     */
    public abstract void call(DeliveryPacket packet, ChannelHandlerContext ctx);

    /**
     * Will be overwritten by each {@link DeliveryEventHandler} with the actual class of the capture of the {@link DeliveryPacket} so the
     * {@link DeliveryEventManager} knows for what types of {@link DeliveryPacket}'s to listen for.
     *
     * @return actual capture {@link Class} of the {@link DeliveryPacket}
     */
    public abstract Class<? extends DeliveryPacket> getPacketCapture();

    /**
     * The {@link UUID} is for giving each {@link DeliveryEventHandler} a unique identifier. If the {@link UUID} is empty it will be
     * generated automatically.
     */
    private final UUID uuid;

    public DeliveryEventHandler() {
        this(UUID.randomUUID());
    }

    public DeliveryEventHandler(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns the identifier of the {@link DeliveryEventHandler}.
     *
     * @return Identifier of the {@link DeliveryEventHandler}
     */
    public UUID getUuid() {
        return this.uuid;
    }

}
