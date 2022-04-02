package at.elias.sql;

import java.sql.*;

public class SqlManager {


    public void createTable(String table) {
        SQLAPI.getInstance().getMySQL().updateMySQL("CREATE TABLE IF NOT EXISTS " + table.toLowerCase() + " (UID VARCHAR(100), TYPE TEXT, VALUE TEXT)");
    }

    public boolean isExist(String id, String table, String type) {
        try {
            PreparedStatement ps = SQLAPI.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE UID = ? AND TYPE = ?");
            ps.setString(1, id);
            ps.setString(2, type.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return (rs.getString("TYPE") != null);
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void create(String id, String table, String type) {
        if (!isExist(id, table, type))
            try {
                PreparedStatement ps = SQLAPI.getInstance().getMySQL().getConnection().prepareStatement("INSERT INTO " + table.toLowerCase() + " (UID, TYPE, VALUE) VALUES (?, ?, ?)");
                ps.setString(1, id);
                ps.setString(2, type.toUpperCase());
                ps.setInt(3, 0);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    public String getString(String id, String table, String type) {
        try {
            PreparedStatement ps = SQLAPI.getInstance().getMySQL().getConnection().prepareStatement("SELECT VALUE FROM " + table.toLowerCase() + " WHERE UID = ? AND TYPE = ?");
            ps.setString(1, id);
            ps.setString(2, type.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("VALUE");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    public void setString(String id, String table, String type, String value) {
        if (isExist(id, table, type))
            try {
                PreparedStatement ps = SQLAPI.getInstance().getMySQL().getConnection().prepareStatement("UPDATE " + table.toLowerCase() + " SET VALUE = ? WHERE UID = ? AND TYPE = ?");
                ps.setString(1, value);
                ps.setString(2, id);
                ps.setString(3, type.toUpperCase());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

}
