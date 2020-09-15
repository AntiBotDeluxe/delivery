package de.antibotdeluxe.delivery.codec;

/**
 * In order to access the data in each specific {@link DeliveryPacket} each packet has to implement {@link DeliveryPacket} and overwrite
 * the methods and fill them to the specific needs.
 * <p>
 * The {@link DeliveryPacket} will be used as template to generalise the data transfer. It's mandatory that {@link DeliveryPacket}'s from
 * the same type have the data set at the same point in the {@link DeliveryByteBuf}. Other wise the encoding and decoding would not be
 * possible.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public interface DeliveryPacket {

    /**
     * Write simple data to the {@link DeliveryByteBuf} which will than be converted into bytes which can then be sent by the
     * <a target="_blank" href="http://netty.io">Netty.io</a> library.
     *
     * @param buf
     *          Data is written in the {@link DeliveryByteBuf}
     *
     * @see io.netty.buffer.ByteBuf
     */
    void write(DeliveryByteBuf buf);

    /**
     * Used to read out data from the {@link DeliveryByteBuf} which has been sent from <i>another</i> application via the
     * <a target="_blank" href="http://netty.io">Netty.io</a> library and has been converted into the {@link DeliveryByteBuf}.
     *
     * @param buf
     *          {@link DeliveryByteBuf} which contains all the data which was received by the
     *          <a target="_blank" href="http://netty.io">Netty.io</a>
     */
    void read(DeliveryByteBuf buf);

}
