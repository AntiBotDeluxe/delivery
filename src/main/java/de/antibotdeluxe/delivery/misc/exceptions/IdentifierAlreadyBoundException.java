package de.antibotdeluxe.delivery.misc.exceptions;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler} identifier is already bound to a
 * {@link de.antibotdeluxe.delivery.event.DeliveryEventHandler}.<br>
 * The {@link PortAlreadyBoundException} is just a regular {@link RuntimeException} with just a different name to make it easier to
 * catch it or recognize the error by just looking at the name of it.<br>
 * <strong>Notice</strong><br>
 * The message is already filled in and only the identifier will be passed.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class IdentifierAlreadyBoundException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param identifier
     *          Identifier which will be shown in the msg
     */
    public IdentifierAlreadyBoundException(String identifier) {
        super("The given identifier is already bound to another handler {identifier: '" + identifier + "'}");
    }

  }