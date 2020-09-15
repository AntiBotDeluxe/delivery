package de.antibotdeluxe.delivery.codec;

import de.antibotdeluxe.delivery.misc.exceptions.NoSuchPacketException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Class will decode the {@link ByteBuf} from <a target="_blank" href="http://netty.io">Netty.io</a>
 * and parse it into a {@link DeliveryByteBuf} which will then be used to create the {@link DeliveryPacket} in order to work with the data.
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryByteDecoder extends ByteToMessageDecoder {

    private final DeliveryPacketVault vault;

    protected DeliveryByteDecoder(DeliveryPacketVault vault) {
        this.vault = vault;
    }

    /**
     * Will decode the bytes from <a target="_blank" href="http://netty.io">Netty.io</a> into a {@link DeliveryPacket}.
     *
     * @param ctx
     *          {@link ChannelHandlerContext} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     * @param in
     *          {@link ByteBuf} provided by <a target="_blank" href="http://netty.io">Netty.io</a>
     * @param out
     *          {@link List} the finished decoded {@link DeliveryPacket} wil be added to
     * @throws Exception
     *          Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int packetId = in.readInt();
        Class<? extends DeliveryPacket> packetClass = this.vault.getPacket(packetId);
        if (packetClass == null)
            throw new NoSuchPacketException(packetId);
        DeliveryPacket packet = packetClass.getDeclaredConstructor().newInstance();
        packet.read(new DeliveryByteBuf(in));
        in.clear();
        out.add(packet);
    }

}
