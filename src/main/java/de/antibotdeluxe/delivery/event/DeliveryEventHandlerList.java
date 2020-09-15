package de.antibotdeluxe.delivery.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import de.antibotdeluxe.delivery.misc.exceptions.IdentifierAlreadyBoundException;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is used to store all specific {@link DeliveryEventHandler}'s (further called handlers) and call them if specific
 * {@link DeliveryPacket} was detected in the {@link DeliveryEventManager}.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryEventHandlerList {

    /**
     * List containing all {@link DeliveryEventHandler}'s for a specific type of {@link DeliveryPacket}.
     */
    private final List<DeliveryEventHandler> handlers = new ArrayList<>();

    /**
     * Stores which type of {@link DeliveryPacket} is used in this {@link DeliveryEventHandlerList}.
     */
    private Class<? extends DeliveryPacket> packetCapture;

    /**
     * Will add a new {@link DeliveryEventHandler} to the list.
     *
     * @param handler
     *          {@link DeliveryEventHandler} which should be added
     */
    void addHandler(DeliveryEventHandler handler) {
        if (packetCapture == null)
            packetCapture = handler.getPacketCapture();

        for (DeliveryEventHandler storedHandler : this.handlers) {
            if (storedHandler.getIdentifier().equals(handler.getIdentifier()))
                throw new IdentifierAlreadyBoundException(handler.getIdentifier());
        }
        this.handlers.add(handler);
    }

    /**
     * Executes the stored code in the {@link DeliveryEventHandler}.
     *
     * @param packet
     *          {@link DeliveryPacket} which has the same type as the capture
     * @param context
     *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     */
    void callHandlers(DeliveryPacket packet, ChannelHandlerContext context) {
        for (DeliveryEventHandler listener : this.handlers)
            listener.call(packet, context);
    }

    /**
     * Returns the actual capture of the {@link DeliveryPacket}.
     *
     * @return Class that extends {@link DeliveryPacket}
     */
    public Class<? extends DeliveryPacket> getPacketCapture() {
        return this.packetCapture;
    }
}
