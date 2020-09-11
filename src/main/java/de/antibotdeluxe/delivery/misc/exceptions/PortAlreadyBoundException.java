package de.antibotdeluxe.delivery.misc.exceptions;

/**
 * Exception will be thrown if a port is already in use.<br>
 * The {@link PortAlreadyBoundException} is just a regular {@link RuntimeException} with just a different name to make it easier to
 * catch it or recognize the error by just looking at the name of it.<br>
 * There is no special functionality added to this exception expect the <code>2nd</code> constructor which takes an <code>int port</code>
 * as parameter.
 *
 * @author jhz
 */

public class PortAlreadyBoundException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param msg
     *          Message which should be printed if the exception is fired
     */
    public PortAlreadyBoundException(String msg) {
        super(msg);
    }

    /**
     * The constructor has an already filled in message but it grants the possibility to add the specific port, which was not suitable to
     * the message, so it is easier to search for the error.
     *
     * @see de.antibotdeluxe.delivery.misc.Utility <code>checkPortAvailability(int port)</code> for the port regulations
     *
     * @param port
     *          Port which violated the regulations
     */
    public PortAlreadyBoundException(int port) {
        super("The given port is already bound to another service {port: " + port + "}");
    }

}
