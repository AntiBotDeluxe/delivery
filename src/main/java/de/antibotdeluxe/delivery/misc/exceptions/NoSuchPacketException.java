package de.antibotdeluxe.delivery.misc.exceptions;

import de.antibotdeluxe.delivery.codec.DeliveryPacketVault;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.codec.DeliveryPacket} id was not found int the
 * {@link DeliveryPacketVault }<br>
 * The {@link NoSuchPacketException} is just a regular {@link RuntimeException} with just a
 * different name to make it easier to catch it or recognize the error by just looking at the name
 * of it.<br>
 * <p>
 * <strong>Notice</strong><br>
 * The message is already filled in and only the packetId will be passed.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class NoSuchPacketException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param packetId
     *          PacketId which was not found in the {@link DeliveryPacketVault}
     */
    public NoSuchPacketException(int packetId) {
        super("The packet with the packet id couldn't be found {packetId: " + packetId + "}");
    }

}
