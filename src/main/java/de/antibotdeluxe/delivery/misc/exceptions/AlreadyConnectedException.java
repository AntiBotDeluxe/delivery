package de.antibotdeluxe.delivery.misc.exceptions;

import io.netty.channel.Channel;

/**
 * Exception will be thrown if a specific {@link de.antibotdeluxe.delivery.client.DeliveryClient} is already connected to a
 * {@link de.antibotdeluxe.delivery.server.DeliveryServer} but the application wants to connect to another
 * {@link de.antibotdeluxe.delivery.server.DeliveryServer}.
 * <strong>Notice</strong><br>
 * The message is already filled in and only the {@link Channel} will be passed.
 *
 * @author jhz
 */

public class AlreadyConnectedException extends RuntimeException {

    /**
     * Constructor takes the message to the super class which is then displayed.
     *
     * @param channel
     *          {@link Channel}
     */
    public AlreadyConnectedException(Channel channel) {
        super("The client is already connected to a server. {address: '" + channel.remoteAddress().toString() + "'}");
    }

}
