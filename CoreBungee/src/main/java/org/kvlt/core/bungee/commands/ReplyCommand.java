package org.kvlt.core.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

@Deprecated
public class ReplyCommand extends Command {

    public ReplyCommand() {
        super("r");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
//        if (args.length == 0) return;
//        if (!(commandSender instanceof ProxiedPlayer)) return;
//
//        ProxiedPlayer me = (ProxiedPlayer) commandSender;
//        String myName = me.getName();
//        String message = String.join(" ", args);
//        String serverName = me.getServer().getInfo().getName();
//
//        if (ReplyStorage.hasLastInterlocutor(myName)) {
//            ProxiedPlayer interlocutor = ReplyStorage.getLastInterlocutor(myName);
//            String interlocutorName = interlocutor.getName();
//            PlayerMessagePacketOld pmp = new PlayerMessagePacketOld(serverName, myName, interlocutorName, message);
//
//            CoreBungee.get().sendPacket(pmp);
//        } else {
//            me.sendMessage(new TextComponent("Некому отвечать."));
//        }
    }
}
