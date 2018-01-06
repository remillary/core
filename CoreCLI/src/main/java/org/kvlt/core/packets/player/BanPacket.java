package org.kvlt.core.packets.player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.Core;
import org.kvlt.core.db.PlayerFactory;
import org.kvlt.core.entities.Group;
import org.kvlt.core.entities.ServerPlayer;
import org.kvlt.core.metrics.PlayedTimeCounter;
import org.kvlt.core.packets.MessagePacket;
import org.kvlt.core.protocol.PacketIn;
import org.kvlt.core.protocol.PacketUtil;
import org.kvlt.core.protocol.Packets;
import org.kvlt.core.utils.Log;

public class BanPacket implements PacketIn {

    private String enforcer;
    private String victim;
    private String time;
    private String reason;

    @Override
    public void read(ByteBuf in) {
        enforcer = PacketUtil.readString(in);
        victim = PacketUtil.readString(in);
        time = PacketUtil.readString(in);
        reason = PacketUtil.readString(in);
    }

    @Override
    public void execute(Channel channel) {
        String response;
        int level;

        if (!enforcer.equalsIgnoreCase("console")) {
            ServerPlayer enforcerPlayer = Core.get().getOnlinePlayers().get(enforcer);
            if (enforcerPlayer != null) {
                level = Group.getGroup(enforcerPlayer.getGroup()).getLevel();
            } else {
                return;
            }
        } else {
            level = 1000;
        }

        if (level >= Group.JUNIOR.getLevel()) {
            ServerPlayer victimPlayer;

            if (Core.get().getOnlinePlayers().contains(victim)) {
                victimPlayer = Core.get().getOnlinePlayers().get(victim);
            } else {
                victimPlayer = PlayerFactory.loadPlayer(victim, false);
            }

            if (victimPlayer != null) {
                long parsedTime = PlayedTimeCounter.parseTime(time);

                victimPlayer.setBanned(true);
                victimPlayer.setBannedBy(enforcer);
                victimPlayer.setBannedUntil(parsedTime);
                victimPlayer.setBanAmount(victimPlayer.getBanAmount() + 1);
                victimPlayer.setBanReason(reason);

                PlayerFactory.updatePlayer(victimPlayer);
                new KickPacket(victim, String.format("БАН: %s", reason)).send();

                Log.$(String.format("%s забанил %s: %s",
                        enforcer,
                        victim,
                        reason));

                response = String.format("Вы забанили %s.", victim);
            } else {
                response = "Нет такого игрока в базе!";
            }
        } else {
            response = "Недостаточно прав!";
        }

        MessagePacket responsePacket = new MessagePacket(enforcer, response);
        responsePacket.send(channel);
    }

    @Override
    public int getId() {
        return Packets.BAN_PACKET;
    }

}
