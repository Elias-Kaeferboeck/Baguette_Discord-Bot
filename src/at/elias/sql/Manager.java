package at.elias.sql;

public class Manager {

    public void createTable(String table) {
        new SqlManager().createTable(table);
    }

    public void create(String id, String table, String type) {
        new SqlManager().create(id, table, type);
    }

    public void setString(String id, String table, String type, String value) {
        new SqlManager().setString(id, table, type, value);
    }

    public String getString(String id, String table, String type) {
        return new SqlManager().getString(id, table, type);
    }
}
