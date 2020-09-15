package de.antibotdeluxe.delivery.codec;

import de.antibotdeluxe.delivery.misc.Utility;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class holds additional methods and provides easier access to the original ByteBuf by
 * <a target="_blank" href="http://netty.io">Netty.io</a>
 * <p>
 * The {@link ByteBuf} holds the different bytes until they are transferred by the <a target="_blank" href="http://netty.io">Netty.io</a>
 * library and are reassembled at the other end of the application where they are added to a new {@link ByteBuf} instance and can be used
 * by the application.
 *
 * @see ByteBuf
 * @see io.netty.buffer.AbstractByteBuf
 *
 * @author jhz
 */
@SuppressWarnings("unused")
public class DeliveryByteBuf {

    /**
     * The original {@link ByteBuf} from the <a target="_blank" href="http://netty.io">Netty.io</a> project.
     */
    final ByteBuf buf;

    /**
     * Constructor which takes a {@link ByteBuf} from <a target="_blank" href="http://netty.io">Netty.io</a> to read or write data.
     *
     * @param buf
     *          {@link ByteBuf} which contains data or the data should be written to
     */
    public DeliveryByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    /**
     * Writes a {@link Boolean} to the {@link ByteBuf}
     *
     * @param param
     *          {@link Boolean} which should be written to the {@link ByteBuf}
     */
    public void writeBool(boolean param) {
        this.buf.writeBoolean(param);
    }

    /**
     * Will return a {@link Boolean} which was red from the {@link ByteBuf}.
     *
     * @return {@link Boolean} from the {@link ByteBuf}
     */
    public boolean readBool() {
        return this.buf.readBoolean();
    }

    /**
     * Writes a {@link Byte} to the {@link ByteBuf}
     *
     * @param param
     *          {@link Byte} which should be written to the {@link ByteBuf}
     */
    public void writeByte(byte param) {
        this.buf.writeByte(param);
    }

    /**
     * Will return a {@link Byte} which was red from the {@link ByteBuf}.
     *
     * @return {@link Byte} from the {@link ByteBuf}
     */
    public byte readByte() {
        return this.buf.readByte();
    }

    /**
     * Writes a {@link Short} to the {@link ByteBuf}
     *
     * @param param
     *          {@link Short} which should be written to the {@link ByteBuf}
     */
    public void writeShort(short param) {
        this.buf.writeShort(param);
    }

    /**
     * Will return a {@link Short} which was red from the {@link ByteBuf}.
     *
     * @return {@link Short} from the {@link ByteBuf}
     */
    public short readShort() {
        return this.buf.readShort();
    }

    /**
     * Writes an {@link Integer} to the {@link ByteBuf}
     *
     * @param param
     *          {@link Integer} which should be written to the {@link ByteBuf}
     */
    public void writeInt(int param) {
        this.buf.writeInt(param);
    }

    /**
     * Will return an {@link Integer} which was red from the {@link ByteBuf}.
     *
     * @return {@link Integer} from the {@link ByteBuf}
     */
    public int readInt() {
        return this.buf.readInt();
    }

    /**
     * Writes a {@link Long} to the {@link ByteBuf}
     *
     * @param param
     *          {@link Long} which should be written to the {@link ByteBuf}
     */
    public void writeLong(long param) {
        this.buf.writeLong(param);
    }

    /**
     * Will return a {@link Long} which was red from the {@link ByteBuf}.
     *
     * @return {@link Long} from the {@link ByteBuf}
     */
    public long readLong() {
        return this.buf.readLong();
    }

    /**
     * Writes a {@link String} to the {@link ByteBuf}
     *
     * @param param
     *          {@link String} which should be written to the {@link ByteBuf}
     */
    public void writeString(String param) {
        if (param == null || param.isEmpty())
            throw new NullPointerException("The given parameter which should be written in the DeliveryByteBuf is empty or null");
        this.buf.writeInt(param.length());
        this.buf.writeBytes(param.getBytes());
    }

    /**
     * Will return a {@link String} which was red from the {@link ByteBuf}.
     *
     * @return {@link Long} from the {@link ByteBuf}
     */
    public String readString() {
        int length = this.buf.readInt();
        byte[] bytes = new byte[length];
        this.buf.readBytes(bytes, 0, length);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Writes a {@link List} to the {@link ByteBuf}
     *
     * @param param
     *          {@link List} which should be written to the {@link ByteBuf}
     */
    public <T> void writeList(List<T> param) {
        this.writeString(Utility.convertListToString(param));
    }

    /**
     * Will return a {@link List} which was red from the {@link ByteBuf}.
     *
     * @return {@link List} from the {@link ByteBuf}
     */
    public List<String> readList() {
        return Utility.convertStringToList(this.readString());
    }

    /**
     * Return the original {@link ByteBuf}.
     * It will be used by <a target="_blank" href="http://netty.io">Netty.io</a> to transfer the data.
     *
     * @return ByteBuf including the changed values
     */
    public ByteBuf getBuf() {
        return this.buf;
    }
}
