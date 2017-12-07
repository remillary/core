package org.kvlt.core.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.nodes.Proxy;
import org.kvlt.core.protocol.PacketIn;
import org.kvlt.core.protocol.PacketUtil;

public class ProxyConnectPacket implements PacketIn {

    private String name;

    @Override
    public void read(ByteBuf in) {
        name = PacketUtil.readString(in);
    }

    @Override
    public void execute(Channel channel) {
        System.out.println(String.format("Прокси-севрер присоединен (%s)", name));
        new Proxy(name, channel);
    }

    @Override
    public int getId() {
        return 1;
    }

    public String getName() {
        return name;
    }

}
