package org.kvlt.core.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MsgCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
//        if (args.length < 2) return false;
//
//        String recipient = args[0];
//        String[] words = Arrays.copyOfRange(args, 1, args.length);
//        String message = String.join(" ", words);
//
//        PlayerMessagePacketOld pmp = new PlayerMessagePacketOld(
//                ConfigManager.getClientName(),
//                sender.getName(),
//                recipient,
//                message);
//        ConnectionManager.get().sendPacket(pmp);
        return true;
    }
}
