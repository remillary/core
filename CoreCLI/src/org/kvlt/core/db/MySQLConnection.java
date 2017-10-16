package org.kvlt.core.db;

import org.kvlt.core.Config;
import org.kvlt.core.utils.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQLConnection {

    private static MySQLConnection instance;

    private Connection connection;

    private MySQLConnection() {}

    public void connect() {
        String host = Config.getMySQL("host");
        String port = Config.getMySQL("port");
        String db = Config.getMySQL("db");
        String dbUser = Config.getMySQL("user");
        String dbPassword = Config.getMySQL("password");

        String dbParams = "useUnicode=true&" +
                "useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&" +
                "serverTimezone=UTC";

        String dbUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, db)
                .concat("?")
                .concat(dbParams);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM test WHERE id=1");
            set.next();
            String name = set.getString("name");
            Log.$(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MySQLConnection get() {
        return instance == null ? instance = new MySQLConnection() : instance;
    }

}
