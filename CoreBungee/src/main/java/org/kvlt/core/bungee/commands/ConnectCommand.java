package org.kvlt.core.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.kvlt.core.bungee.net.ConnectionManager;

public class ConnectCommand extends Command {

    public ConnectCommand() {
        super("con", null, "connect", "cc", "core");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ConnectionManager.get().connect();
    }

}
