package de.antibotdeluxe.delivery.misc.exceptions;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.codec.DeliveryPacket} id was not found int the
 * {@link de.antibotdeluxe.delivery.codec.DeliveryPacketRegistry }<br>
 * The {@link NoSuchPacketException} is just a regular {@link RuntimeException} with just a
 * different name to make it easier to catch it or recognize the error by just looking at the name
 * of it.<br>
 * There is no special functionality added to this exception.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class NoSuchPacketException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param msg
     *          Message which should be printed if the exception is fired
     */
    public NoSuchPacketException(String msg) {
        super(msg);
    }

}
