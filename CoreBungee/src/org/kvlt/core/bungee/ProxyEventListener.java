package org.kvlt.core.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.kvlt.core.bungee.entities.PlayerAdapter;
import org.kvlt.core.entities.OnlinePlayer;
import org.kvlt.core.packets.player.PlayerProxyLoginPacket;
import org.kvlt.core.packets.player.PlayerProxyQuitPacket;
import org.kvlt.core.packets.player.PlayerSwitchServerPacket;

public class ProxyEventListener implements Listener {

    @EventHandler
    public void onConnection(PostLoginEvent event) {
        ProxiedPlayer p = event.getPlayer();
        OnlinePlayer player = PlayerAdapter.asOnlinePlayer(p);
        String proxyName = CoreBungee.get().getServerName();

        player.setIp(p.getAddress().getHostString());

        PlayerProxyLoginPacket plp = new PlayerProxyLoginPacket(player, proxyName);
        CoreBungee.get().sendPacket(plp);
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String to = player.getServer().getInfo().getName();

        PlayerSwitchServerPacket packet = new PlayerSwitchServerPacket(player.getName(), to);
        CoreBungee.get().sendPacket(packet);
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer p = event.getPlayer();
        PlayerProxyQuitPacket ppqp = new PlayerProxyQuitPacket(p.getName());
        CoreBungee.get().sendPacket(ppqp);
    }

}
