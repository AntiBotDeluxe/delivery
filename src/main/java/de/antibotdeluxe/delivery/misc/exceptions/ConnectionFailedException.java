package de.antibotdeluxe.delivery.misc.exceptions;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.client.DeliveryClient} is not able to connect to a
 * {@link de.antibotdeluxe.delivery.server.DeliveryServer}.
 * <strong>Notice</strong><br>
 * The message is already filled in and only the host address and host port will be passed.
 *
 * @author jhz
 */

public class ConnectionFailedException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param host
     *          host address
     * @param port
     *          host port
     */
    public ConnectionFailedException(String host, int port) {
        super("The connection has failed! {address: '" + host + ":" + port + "'}");
    }

}