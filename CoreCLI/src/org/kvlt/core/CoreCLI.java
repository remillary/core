package org.kvlt.core;

import org.kvlt.core.config.Config;
import org.kvlt.core.db.DB;
import org.kvlt.core.db.MySQLConnection;

public class CoreCLI {

    private CoreCLI() {}

    public static void main(String[] args) {
        Config.init();
        DB.getMySQLConnection().connect();

        CoreServer.get().start();
    }

}
