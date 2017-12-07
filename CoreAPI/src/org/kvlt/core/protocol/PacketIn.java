package org.kvlt.core.protocol;

import io.netty.buffer.ByteBuf;

public abstract class PacketIn implements Packet {

    public abstract void read(ByteBuf in);
    public abstract void execute();

}
