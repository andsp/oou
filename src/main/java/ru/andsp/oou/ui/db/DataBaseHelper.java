package ru.andsp.oou.ui.db;


import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DataBaseHelper implements Closeable {


    Connection connection;

    private void connect() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:oou.s3db");
    }

    private void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE if not exists 'instance' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'path' text,'host' text, 'port' text,'user' text,'pass' text,'db' text);");
        }
    }

    private boolean isConnected() {
        return connection != null;
    }


    DataBaseHelper() throws SQLException, ClassNotFoundException {
        connect();
        if (isConnected())
            createTable();
    }


    public void close() throws IOException {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
}
