package org.kvlt.core.datahandlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.kvlt.core.packets.Packet;
import org.kvlt.core.packets.Packets;

import java.io.IOException;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

public class PacketFramer extends ByteToMessageCodec<Packet> {

    private final int MIN_BYTES = 8;
    private final int MAX_BYTES = 128;
    private final int DATA_PART_INDEX = 8;
    private final int SIZE_SHORT_INDEX = 6;
    private final int MIN_PACKET_ID = 1;


    // prefix for each packet
    // >core
    private final byte[] PREFIX = {
            0x3E,       // >
            0x63, 0x6F, // c o
            0x72, 0x65  // r e
    };

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws IllegalArgumentException {
        int id = Packets.getIdByClass(packet.getClass());

        if (id < 1) {
            throw new IllegalArgumentException("id must be bigger 1");
        }

        byteBuf.writeBytes(PREFIX);
        byteBuf.writeByte(id);

        byteBuf.writerIndex(DATA_PART_INDEX);

        packet.writeBytes(byteBuf);

        byteBuf.setShort(SIZE_SHORT_INDEX, (short) byteBuf.readableBytes());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IOException {
        int readable = byteBuf.readableBytes();

        if (readable < MIN_BYTES || readable > MAX_BYTES) {
            return;
        }

        byte[] receivedPrefix = ByteBufUtil.getBytes(byteBuf.readBytes(5));
        if (Arrays.equals(receivedPrefix, PREFIX)) {

            byte id = byteBuf.readByte();
            if (id >= MIN_PACKET_ID) {

                int length = byteBuf.readShort();
                if (length >= MIN_BYTES) {

                    Packet p = Packets.getById(id);
                    if (p != null && readable == length) {
                        p.readBytes(byteBuf);
                        list.add(p);
                    } else {
                        throw new IOException("Invalid packet with [id = " + id + "]");
                    }

                }
            }
        }
    }

}
