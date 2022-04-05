package at.elias.sql;

public class Manager {

    public void createTable(String table) {
        try {
            new SqlManager().createTable(table);
        } catch (Exception e) {
            SQLAPI.getInstance().getMySQL().disconnectFromMySQL();
            SQLAPI.getInstance().getMySQL().connectToMySQL();
            createTable(table);
        }
    }

    public void create(String id, String table, String type) {
        try {
            new SqlManager().create(id, table, type);
        } catch (Exception e) {
            SQLAPI.getInstance().getMySQL().disconnectFromMySQL();
            SQLAPI.getInstance().getMySQL().connectToMySQL();
            create(id, table, type);
        }
    }

    public void setString(String id, String table, String type, String value) {
        try {
            new SqlManager().setString(id, table, type, value);
        } catch (Exception e) {
            SQLAPI.getInstance().getMySQL().disconnectFromMySQL();
            SQLAPI.getInstance().getMySQL().connectToMySQL();
            setString(id, table, type, value);
        }
    }

    public String getString(String id, String table, String type) {
        try {
            return new SqlManager().getString(id, table, type);
        } catch (Exception e) {
            SQLAPI.getInstance().getMySQL().disconnectFromMySQL();
            SQLAPI.getInstance().getMySQL().connectToMySQL();
            return getString(id, table, type);
        }
    }
}
