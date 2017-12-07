package org.kvlt.core.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.nodes.GameServer;
import org.kvlt.core.protocol.PacketIn;
import org.kvlt.core.protocol.PacketUtil;

public class ServerConnectPacket implements PacketIn {

    private String name;

    @Override
    public void read(ByteBuf in) {
        name = PacketUtil.readString(in);
    }

    @Override
    public void execute(Channel channel) {
        System.out.println(String.format("Сервер присоединен (%s)", name));
        new GameServer(name, channel);
    }

    @Override
    public int getId() {
        return 3;
    }

}
