package de.antibotdeluxe.delivery.tests.event.packets;

import de.antibotdeluxe.delivery.codec.DeliveryByteBuf;
import de.antibotdeluxe.delivery.codec.DeliveryPacket;

/**
 * Class simulates a {@link DeliveryPacket} in Runtime.
 *
 * @author jhz
 */
public class JaneDoePacket implements DeliveryPacket {

    @Override
    public void write(DeliveryByteBuf buf) { }

    @Override
    public void read(DeliveryByteBuf buf) { }

    public String sayName() {
        return "Jane Doe";
    }

}
