package de.antibotdeluxe.delivery.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Class will encode the {@link DeliveryPacket} and write it to the {@link ByteBuf} so
 * <a target="_blank" href="http://netty.io">Netty.io</a> is able to transfer the raw data.
 * <p>
 * The encoding is really simple.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryByteEncoder extends MessageToByteEncoder<DeliveryPacket> {

    private final DeliveryPacketVault vault;

    public DeliveryByteEncoder(DeliveryPacketVault vault) {
        this.vault = vault;
    }

    /**
     * Will write the data from the {@link DeliveryPacket} to the {@link DeliveryByteBuf} and then transfers it.
     * The {@link DeliveryPacket} id will be written in front of the data so the {@link DeliveryByteEncoder} is able to recognize the
     * {@link DeliveryPacket} and can parse it in the right {@link DeliveryPacket}.
     *
     * @param ctx
     *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     * @param msg
     *          {@link DeliveryPacket} which should be encoded
     * @param out
     *          {@link ByteBuf} the data will be written to. Provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, DeliveryPacket msg, ByteBuf out) {
        int packetId = this.vault.getPacketId(msg);
        out.writeInt(packetId);
        msg.write(new DeliveryByteBuf(out));
    }

}
