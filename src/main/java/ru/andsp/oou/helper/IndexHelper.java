package ru.andsp.oou.helper;

import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.Index;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.Table;
import ru.andsp.oou.type.TypeObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IndexHelper implements ObjectHelper {

    private static final String UNIQUE = "UNIQUE";
    private static final String BITMAP = "BITMAP";

    private static final String INDEX_QUERY = "select i.index_name, i.table_name, i.uniqueness, i.tablespace_name\n" +
            "  from user_indexes i\n" +
            " where i.table_name = ?\n" +
            " order by i.index_name";
    private static final String INDEX_COLUMN_QUERY = " select c.COLUMN_NAME from user_ind_columns c " +
            "where c.INDEX_NAME = ? order by c.COLUMN_POSITION";


    private void indexColumnLoad(Connection connection, Index index) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INDEX_COLUMN_QUERY)) {
            statement.setString(1, index.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    index.addColumn(resultSet.getString("COLUMN_NAME"));
                }
            }
        }
    }

    public void loadFromTable(Connection connection, Table table) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INDEX_QUERY)) {
            statement.setString(1, table.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Index index = new Index(resultSet.getString("INDEX_NAME"));
                    String typeIndex = resultSet.getString("UNIQUENESS");
                    switch (typeIndex) {
                        case UNIQUE: {
                            index.setUnique(true);
                            break;
                        }
                        case BITMAP: {
                            index.setBitmap(true);
                        }
                    }
                    index.setTableName(table.getName());
                    index.setTablespace(resultSet.getString("TABLESPACE_NAME"));
                    indexColumnLoad(connection, index);
                    table.addIndex(index);
                }
            }
        }
    }


    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        throw new NotImplementedException();
    }
}
