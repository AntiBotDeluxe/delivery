package de.antibotdeluxe.delivery.codec;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Used from the Server and Client to get the {@link DeliveryPacket} id and parse them into the right {@link DeliveryPacket}.
 *
 * @author jhz
 *
 */
@SuppressWarnings("unused")
public class DeliveryPacketVault {

    /**
     * List that holds every {@link DeliveryPacket} registered.
     */
    private final ArrayList<Class<? extends DeliveryPacket>> vault = new ArrayList<>();

    /**
     * Retrieves a specific {@link DeliveryPacket} id by it's class.
     *
     * @param param
     *          {@link DeliveryPacket} which id should be retrieved
     * @return The {@link DeliveryPacket} id
     */
    public int getPacketId(DeliveryPacket param) {
        return this.getPacketIdByClass(param.getClass());
    }

    /**
     * Retrieves a specific {@link DeliveryPacket} id by it's class.
     *
     * @param clazz
     *          {@link DeliveryPacket} which id should be retrieved
     * @return The {@link DeliveryPacket} id
     */
    public int getPacketIdByClass(Class<? extends DeliveryPacket> clazz) {

        return this.vault.indexOf(clazz);
    }

    /**
     * Adds the given {@link DeliveryPacket} as raw class object to the vault.
     *
     * @param packet
     *          {@link DeliveryPacket} which should be registered
     */
    public void addPacket(DeliveryPacket packet) {
        this.addPacketClass(packet.getClass());
    }

    /**
     * Adds the given {@link DeliveryPacket}'s as raw class object to the vault.
     *
     * @param packet
     *          {@link DeliveryPacket} which should be registered
     */
    public void addPackets(DeliveryPacket... packet) {
        for (DeliveryPacket deliveryPacket : packet) {
            this.addPacketClass(deliveryPacket.getClass());
        }
    }

    /**
     * Adds the given {@link DeliveryPacket} as raw class object to the vault.
     *
     * @param param
     *          {@link DeliveryPacket} which should be registered
     */
    public void addPacketClass(Class<? extends DeliveryPacket> param) {
        this.vault.add(param);
    }

    /**
     * Adds the given {@link DeliveryPacket} as raw class object to the vault.
     *
     * @param classes
     *          {@link DeliveryPacket} {@link Class}'s which should be registered
     */
    @SafeVarargs
    public final void addPacketClasses(Class<? extends DeliveryPacket>... classes) {
        Collections.addAll(this.vault, classes);
    }

    /**
     * Returns the {@link DeliveryPacket} {@link Class} object if the corresponding {@link DeliveryPacket} id was found.
     *
     * @param packetId
     *          from the {@link DeliveryPacket}
     * @return DeliveryPacket as {@link Class}
     */
    public Class<? extends DeliveryPacket> getPacket(int packetId) {
        return this.vault.get(packetId);
    }

}
