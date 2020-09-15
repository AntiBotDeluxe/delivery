package de.antibotdeluxe.delivery.tests.packets;

import de.antibotdeluxe.delivery.codec.DeliveryPacketVault;

/**
 * Test {@link DeliveryPacketVault}
 */

public class TestPacketVault extends DeliveryPacketVault {

    /**
     * Adds the captures {@link de.antibotdeluxe.delivery.codec.DeliveryPacket}'s to the {@link DeliveryPacketVault}.
     */
    public TestPacketVault() {
        addPacketClasses(TestJaneDoePacket.class, TestLisaEmberPacket.class);
    }

}
