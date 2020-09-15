package de.antibotdeluxe.delivery.misc.exceptions;

/**
 * Exception will be thrown if a port is already in use.<br>
 * The {@link PortAlreadyBoundException} is just a regular {@link RuntimeException} with just a different name to make it easier to
 * catch it or recognize the error by just looking at the name of it.<br>
 * <p>
 * <strong>Notice</strong><br>
 * The message is already filled in and only the port will be passed.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class PortAlreadyBoundException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
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
