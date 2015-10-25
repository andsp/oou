package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;
import ru.andsp.oou.type.View;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewHelper implements ObjectHelper {


    private static final String VIEW_QUERY = "select v.text from all_views v\n" +
            "where v.VIEW_NAME = ? ";

    private PreparedStatement statement;


    private PreparedStatement getStatement(Connection connection, String name) throws SQLException {
        if (this.statement == null) {
            this.statement = connection.prepareStatement(VIEW_QUERY);
        }
        this.statement.setString(1, name.toUpperCase());
        return this.statement;
    }

    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        View view = new View(name);
        try (ResultSet resultSet = getStatement(dataSource.getConnection(), name).executeQuery()) {
            while (resultSet.next()) {
                view.setSource(resultSet.getString("TEXT"));
            }
        }
        return view;
    }

    @Override
    public void close() throws IOException {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            statement = null;
        }
    }
}