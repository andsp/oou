package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.Synonym;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SynonymHelper implements ObjectHelper {

    private static final String SYNONYM_QUERY = "select s.table_owner,\n" +
            "       s.table_name\n" +
            "  from all_synonyms s\n" +
            " where s.SYNONYM_NAME = ?";

    private PreparedStatement statement;


    private PreparedStatement getStatement(Connection connection, String name) throws SQLException {
        if (this.statement == null) {
            this.statement = connection.prepareStatement(SYNONYM_QUERY);
        }
        this.statement.setString(1, name.toUpperCase());
        return this.statement;
    }

    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        Synonym synonym = null;
        try (ResultSet resultSet = getStatement(dataSource.getConnection(), name).executeQuery()) {
            while (resultSet.next()) {
                synonym = new Synonym(name);
                synonym.setPublicSynonym(false);
                synonym.setOwner(resultSet.getString("TABLE_OWNER"));
                synonym.setNameObject(resultSet.getString("TABLE_NAME"));
            }
        }
        return synonym;
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