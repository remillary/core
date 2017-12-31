package org.kvlt.core.packets.player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.CoreServer;
import org.kvlt.core.db.PlayerFactory;
import org.kvlt.core.entities.ServerPlayer;
import org.kvlt.core.packets.MessagePacket;
import org.kvlt.core.protocol.PacketUtil;
import org.kvlt.core.utils.Log;

import static org.kvlt.core.protocol.Packets.PLAYER_REG_PACKET;

public class PlayerRegisterPacket extends PlayerPacket {

    private ServerPlayer unloggedPlayer;
    private String name;
    private String password;

    @Override
    public void read(ByteBuf in) {
        name = PacketUtil.readString(in);
        password = PacketUtil.readString(in);

        setPlayerName(name);
    }

    @Override
    public void execute(Channel channel) {
        unloggedPlayer = CoreServer.get().getUnloggedPlayers().get(name);
        if (unloggedPlayer == null) return;

        String response;
        String dbPassword = unloggedPlayer.getPassword();

        if (!ensurePlayer()) { // checking if player is logged in TODO: ITS NOT VALID!
            if (dbPassword == null || dbPassword.isEmpty()) {
                PlayerFactory.register(unloggedPlayer, password);
                Log.$(String.format("%s зарегистрировался.", name));
                response = "Вы успешно зарегистировались!";
            } else {
                response = "Вы уже зарегистрированы!";
            }
        } else {
            response = "Вы уже в игре!";
        }

        AuthPacket authPacket = new AuthPacket(name);
        MessagePacket msg = new MessagePacket(name, response);

        authPacket.send(channel);
        msg.send(channel);

    }

    @Override
    public int getId() {
        return PLAYER_REG_PACKET;
    }
}
