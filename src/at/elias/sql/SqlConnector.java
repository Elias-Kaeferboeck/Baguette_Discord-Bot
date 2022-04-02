package at.elias.sql;

import java.sql.*;

public class SqlConnector {
    private String host;

    private String username;

    private String password;

    private String database;

    private int port;

    private static Connection connection;

    public SqlConnector(String host, String username, String password, String database, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        connectToMySQL();
    }

    public void connectToMySQL() {
        if (!isConnected())
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", this.username, this.password);
                System.out.println("[sql] Connection established!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("[sql] Connection closed! Reason: NO CONNECTION!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void disconnectFromMySQL() {
        if (isConnected())
            try {
                connection.close();
                System.out.println("[sql] Connection closed!");
            } catch (SQLException sQLException) {
            }
    }

    public boolean isConnected() {
        return (connection != null);
    }

    public Connection getConnection() {
        return connection;
    }

    public void updateMySQL(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.executeUpdate();
        } catch (SQLException sQLException) {
        }
    }

    public ResultSet getResult(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            return ps.executeQuery();
        } catch (SQLException sQLException) {
            return null;
        }
    }
}
