package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.Sequence;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceHelper implements ObjectHelper {

    private static final String SEQUENCE_QUERY = "select s.min_value,\n" +
            "       s.max_value,\n" +
            "       s.increment_by,\n" +
            "       s.cycle_flag,\n" +
            "       s.order_flag,\n" +
            "       s.cache_size,\n" +
            "       s.last_number\n" +
            "  from user_sequences s\n" +
            " where s.sequence_name = ?";

    private PreparedStatement statement;


    private PreparedStatement getStatement(Connection connection, String name) throws SQLException {
        if (this.statement == null) {
            this.statement = connection.prepareStatement(SEQUENCE_QUERY);
        }
        this.statement.setString(1, name.toUpperCase());
        return this.statement;
    }

    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        Sequence sequence = null;
        try (ResultSet resultSet = getStatement(dataSource.getConnection(), name).executeQuery()) {
            while (resultSet.next()) {
                sequence = new Sequence(name);
                sequence.setMax(resultSet.getBigDecimal("MAX_VALUE").toBigInteger());
                sequence.setMin(resultSet.getBigDecimal("MIN_VALUE").toBigInteger());
                sequence.setIncrement(resultSet.getBigDecimal("INCREMENT_BY").toBigInteger());
                sequence.setStart(resultSet.getBigDecimal("LAST_NUMBER").toBigInteger());
                sequence.setCache(resultSet.getBigDecimal("CACHE_SIZE").toBigInteger());
                sequence.setCycle(resultSet.getString("CYCLE_FLAG").equals("N"));
                sequence.setOrder(resultSet.getString("ORDER_FLAG").equals("N"));
            }
        }
        return sequence;
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