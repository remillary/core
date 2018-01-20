package org.kvlt.core.bungee;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.kvlt.core.bungee.storages.Infractions;
import org.kvlt.core.bungee.storages.ProxyLoggedPlayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthEventListener implements Listener {

    private static List<String> allowedCmds;
    private static Map<ProxiedPlayer, Long> cooldowns;

    static {
        cooldowns = new HashMap<>();
        allowedCmds = new ArrayList<String>() {{
            add("/register");
            add("/reg");
            add("/l");
            add("/login");
            add("/email");
        }};
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
        String playerName = sender.getName();
        String message = event.getMessage();

        if (event.isCommand() && !event.isCancelled()) {
            if (cooldowns.containsKey(sender) && cooldowns.get(sender) < System.currentTimeMillis()) {
                sender.sendMessage(new TextComponent("Too frequently!"));
                return;
            }
            cooldowns.put(sender, System.currentTimeMillis() + 2000L);
        }

        if (!ProxyLoggedPlayers.isLogged(playerName)) {
            if (event.isCommand()) {
                String command = message.trim().split("\\s")[0];
                if (!allowedCmds.contains(command.toLowerCase())) {
                    event.setCancelled(true);
                    return;
                }
            } else {
                event.setCancelled(true);
                return;
            }
        }

        if (Infractions.isMuted(playerName)) {
            if (!event.isCommand()) {
                sender.sendMessage(new TextComponent("У вас мут!"));
                event.setCancelled(true);
            }
        }

//        PlayerChatPacket pcp = new PlayerChatPacket(sender.getName(), message, isCommand);
//        pcp.send();
    }

}
