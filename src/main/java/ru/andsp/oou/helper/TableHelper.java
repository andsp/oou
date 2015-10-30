package ru.andsp.oou.helper;

import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableHelper implements ObjectHelper {

    private static final String ON_RESERVE = "SYS$SESSION";

    private static final String TABLE_QUERY = "select t.table_name,\n" +
            "       t.temporary,\n" +
            "       t.duration,\n" +
            "       (select c.comments\n" +
            "          from user_tab_comments c\n" +
            "         where c.table_name = t.table_name) as comments\n" +
            "  from user_tables t\n" +
            " where table_name = ?";

    private static final String COMMENTS_QUERY = "select c.column_name,\n" +
            "       c.comments\n" +
            "  from user_col_comments c\n" +
            "  join user_tab_cols tc\n" +
            "    on tc.table_name = c.table_name\n" +
            "   and tc.column_name = c.column_name\n" +
            " where c.table_name = ?\n" +
            "   and c.comments is not null\n" +
            " order by tc.column_id";

    private static final String COLUMNS_QUERY = "select c.column_name,\n" +
            "       c.data_type,\n" +
            "       c.data_length,\n" +
            "       c.data_precision,\n" +
            "       c.nullable,\n" +
            "       c.data_default\n" +
            "  from user_tab_columns c\n" +
            " where c.table_name = ?\n" +
            " order by c.COLUMN_ID";


    private Table table;


    private void columnsLoad(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(COLUMNS_QUERY)) {
            statement.setString(1, table.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TableColumn column = new TableColumn(resultSet.getString("COLUMN_NAME"));
                    column.setTypes(DataTypes.valueOf(resultSet.getString("DATA_TYPE")));
                    column.setDecimal(resultSet.getInt("DATA_PRECISION"));
                    column.setLength(resultSet.getInt("DATA_LENGTH"));
                    column.setDefValue(resultSet.getString("DATA_DEFAULT"));
                    column.setNullable(resultSet.getString("").equals("Y"));
                    table.addColumn(column);
                }
            }
        }
    }

    private void columnCommentsLoad(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(COMMENTS_QUERY)) {
            statement.setString(1, table.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TableColumnComment comment = new TableColumnComment();
                    comment.setTabName(table.getName());
                    comment.setColName(resultSet.getString("COLUMN_NAME"));
                    comment.setComment(resultSet.getString("COMMENTS"));
                    table.addColumnComment(comment);
                }
            }
        }
    }

    private void constraintsLoad(Connection connection) throws SQLException {

    }

    private void indexsLoad(Connection connection) throws SQLException {

    }

    private void tableLoad(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(TABLE_QUERY)) {
            statement.setString(1, table.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    table.setTemporary(resultSet.getString("TEMPORARY").equals("Y"));
                    if (table.isTemporary()) {
                        table.setPreserve(resultSet.getString("DURATION").equals(ON_RESERVE));
                    }
                    table.setComment(resultSet.getString("COMMENTS"));
                }
            }
        }
    }

    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        table = new Table(name);
        try (Connection connection = dataSource.getConnection()) {
            tableLoad(connection);
            columnsLoad(connection);
            constraintsLoad(connection);
            indexsLoad(connection);
            columnCommentsLoad(connection);
        }
        return table;
    }

}
