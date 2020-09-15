package de.antibotdeluxe.delivery.tests.packets;

import de.antibotdeluxe.delivery.codec.DeliveryByteBuf;
import de.antibotdeluxe.delivery.codec.DeliveryPacket;

/**
 * Class simulates a {@link DeliveryPacket} in Runtime.
 *
 * @author jhz
 */
public class TestLisaEmberPacket implements DeliveryPacket {

    private String name;

    public TestLisaEmberPacket() { }

    public TestLisaEmberPacket(String name) {
        this.name = name;
    }

    @Override
    public void write(DeliveryByteBuf buf) {
        buf.writeString(name);
    }

    @Override
    public void read(DeliveryByteBuf buf) {
        this.name = buf.readString();
    }

    public String getName() {
        return this.name;
    }

    public String sayName() {
        return "Lisa Ember";
    }

}
