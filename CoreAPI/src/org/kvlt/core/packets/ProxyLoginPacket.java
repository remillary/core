package org.kvlt.core.packets;

import org.kvlt.core.CoreServer;
import org.kvlt.core.entities.CorePlayer;
import org.kvlt.core.entities.ServerPlayer;
import org.kvlt.core.utils.Log;

import java.io.Serializable;

public class ProxyLoginPacket extends Packet implements Serializable {

    private ServerPlayer player;
    private String server;

    public ProxyLoginPacket(ServerPlayer serverPlayer, String server) {
        this.player = serverPlayer;
        this.server = server;
    }

    @Override
    protected void onCore() {
        CorePlayer player = new CorePlayer(getServerPlayer());
        player.setName(player.getName().toLowerCase());

        CoreServer.get().getServerPlayers().add(getServerPlayer());

        Log.$(getServerPlayer().getName() + " присоединился к " + getServer());
    }

    @Override
    protected void onServer() {

    }

    @Override
    protected void onProxy() {

    }

    public ServerPlayer getServerPlayer() {
        return player;
    }

    public String getServer() {
        return server;
    }

}
