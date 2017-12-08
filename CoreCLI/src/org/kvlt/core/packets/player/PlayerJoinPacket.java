package org.kvlt.core.packets.player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.CoreServer;
import org.kvlt.core.entities.OnlinePlayer;
import org.kvlt.core.events.player.PlayerJoinEvent;
import org.kvlt.core.nodes.Proxy;
import org.kvlt.core.protocol.PacketIn;
import org.kvlt.core.protocol.PacketUtil;

public class PlayerJoinPacket implements PacketIn {

    private String playerName;
    private String proxyName;
    private String ip;
    private String uuid;

    @Override
    public void read(ByteBuf in) {
        playerName = PacketUtil.readString(in);
        proxyName = PacketUtil.readString(in);
        ip = PacketUtil.readString(in);
        uuid = PacketUtil.readString(in);
    }

    @Override
    public void execute(Channel channel) {
        OnlinePlayer op = new OnlinePlayer(playerName);
        Proxy proxy = CoreServer.get().getProxies().getNode(proxyName);
        if (proxy == null) return;

        op.setIp(ip);
        op.setUuid(uuid);
        op.setCurrentProxy(proxy);

        CoreServer.get().getOnlinePlayers().add(op);
        CoreServer.get().getEventManager().invokeEvent(new PlayerJoinEvent(op));
    }

    @Override
    public int getId() {
        return 6;
    }
}
