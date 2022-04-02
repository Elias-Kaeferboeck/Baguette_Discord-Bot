package at.elias.sql;

import at.elias.misc.FileUtil;

public class SQLAPI {
    private static SQLAPI instance;

    Manager statsManager;

    SqlConnector mySQL;

    public static SQLAPI getInstance() {
        return instance;
    }

    public Manager getStatsManager() {
        return this.statsManager;
    }

    public SqlConnector getMySQL() {
        return this.mySQL;
    }

    public SQLAPI() {
        instance = this;
        this.statsManager = new Manager();
        String[] information = FileUtil.getSQLInformationFromFile().toArray(new String[0]);
        connectToMySQL(information[0], information[1], information[2], information[3], Integer.parseInt(information[4]));
    }

    private void connectToMySQL(String host, String user, String password, String database, int port) {
        this.mySQL = new SqlConnector(host, user, password, database, port);
    }
}
