package dev.zayatv.database;

import java.sql.*;

public class DatabaseSecrets {
    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DATABASE = "secrets";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private Connection connection;

    public void connect() throws SQLException {
        //createDB();
        connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
    }

    private void createDB() throws SQLException {
        if (isConnected()) return;
        connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/?user=" + USERNAME + "&password=" + PASSWORD);
        connection.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE);
        connection.close();
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getApiKey(String name) {
        String tableName = "api_keys";
        String foundType = "";

        String sql = "SELECT * FROM " + tableName + " WHERE NAME = '" + name + "'";
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(sql);
            ResultSet result = prep.executeQuery();
            result.next();
            foundType = result.getString("ApiKey");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundType;
        /*try {
            return connection.createStatement().executeQuery("SELECT * FROM " + tableName + " WHERE NAME = '" + name + "'").getString("ApiKey");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }*/
    }
}
