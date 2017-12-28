package org.kvlt.core;

import org.kvlt.core.config.Config;
import org.kvlt.core.db.HibernateInitiaizer;
import org.kvlt.core.email.Email;

public class CoreCLI {

    private CoreCLI() {}

    public static void main(String[] args) {
        Config.init();
        HibernateInitiaizer.start();
        CoreServer.get().start();
    }

}
