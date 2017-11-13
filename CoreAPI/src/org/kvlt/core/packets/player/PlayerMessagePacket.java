package org.kvlt.core.packets.player;

import org.bukkit.Bukkit;
import org.kvlt.core.CoreServer;
import org.kvlt.core.entities.OnlinePlayer;
import org.kvlt.core.packets.Packet;
import org.kvlt.core.packets.bukkit.ServerMessagePacket;
import org.kvlt.core.utils.Log;

public class PlayerMessagePacket extends Packet {

    private static final String msgFormat = "(PM) [%from%] %sender%: %msg%";

    private String sender;
    private String recepient;
    private String message;
    private String server;

    public PlayerMessagePacket(String from, String sender, String recepient, String message) {
        this.sender = sender;
        this.recepient = recepient;
        this.message = message;
        this.server = from;
    }

    @Override
    protected void onCore() {
        OnlinePlayer coreRecepient = CoreServer.get().getOnlinePlayers().get(recepient);
        OnlinePlayer coreSender = CoreServer.get().getOnlinePlayers().get(sender);

        if (coreRecepient != null) {
            CoreServer.get().getGameServers().send(this);
        } else {
            String errorMsg = "Такого игрока нет онлайн.";
            ServerMessagePacket smp = new ServerMessagePacket(sender, errorMsg);
            CoreServer.get().getGameServers().getNode(coreSender.getCurrentServer()).send(smp);
        }
    }

    @Override
    protected void onServer() {
        String formattedMsg = msgFormat
                .replaceAll("%from%", server)
                .replaceAll("%sender%", sender)
                .replaceAll("%msg%", message);

        Bukkit.getPlayer(recepient).sendMessage(formattedMsg);
    }

    @Override
    protected void onProxy() {

    }
}
