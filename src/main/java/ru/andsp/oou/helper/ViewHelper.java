package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;
import ru.andsp.oou.type.View;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewHelper implements ObjectHelper {


    private static final String VIEW_QUERY = "select v.text from user_views v where v.VIEW_NAME = ? ";

    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        View view = new View(name);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(VIEW_QUERY)) {
                statement.setString(1, name.toUpperCase());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        view.setText(resultSet.getString("TEXT"));
                    }
                }
            }
        }
        return view;
    }
}