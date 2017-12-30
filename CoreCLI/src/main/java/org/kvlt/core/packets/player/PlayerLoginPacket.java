package org.kvlt.core.packets.player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.CoreServer;
import org.kvlt.core.entities.ServerPlayer;
import org.kvlt.core.events.player.PlayerLoginEvent;
import org.kvlt.core.protocol.PacketIn;
import org.kvlt.core.protocol.PacketUtil;
import org.kvlt.core.protocol.Packets;

public class PlayerLoginPacket implements PacketIn {

    private String playerName;

    @Override
    public void read(ByteBuf in) {
        playerName = PacketUtil.readString(in);
    }

    @Override
    public void execute(Channel channel) {
        ServerPlayer player = CoreServer.get().getUnloggedPlayers().get(playerName);

        int id = player.getId();
        IdPacket idPacket = new IdPacket(playerName, id);
        idPacket.send(channel);

        if (player.isBanned()) {
            new KickPacket(playerName, "Ban Hammer has spoken.").send(channel);
        }

        PlayerLoginEvent ple = new PlayerLoginEvent(player);
        ple.invoke();
    }

    @Override
    public int getId() {
        return Packets.PLAYER_LOGIN_PACKET;
    }
}
