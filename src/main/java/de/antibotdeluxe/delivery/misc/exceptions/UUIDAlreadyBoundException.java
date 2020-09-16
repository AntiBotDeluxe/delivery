package de.antibotdeluxe.delivery.misc.exceptions;

import java.util.UUID;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler} {@link UUID} is already bound to a
 * {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}.<br>
 * The {@link UUIDAlreadyBoundException} is just a regular {@link RuntimeException} with just a different name to make it easier to
 * catch it or recognize the error by just looking at the name of it.<br>
 * <strong>Notice</strong><br>
 * The message is already filled in and only the {@link UUID} will be passed.
 *
 * @author jhz
 */

public class UUIDAlreadyBoundException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param uuid
     *          {@link UUID} which will be shown in the message
     */
    public UUIDAlreadyBoundException(UUID uuid) {
        super("The given UUID is already bound to another handler {uuid: '" + uuid.toString() + "'}");
    }

  }