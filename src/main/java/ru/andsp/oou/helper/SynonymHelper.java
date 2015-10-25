package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.Synonym;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SynonymHelper implements ObjectHelper {

    private static final String SYNONYM_QUERY = "select s.table_owner,\n" +
            "       s.table_name\n" +
            "  from user_synonyms s\n" +
            " where s.SYNONYM_NAME = ?";


    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        Synonym synonym = new Synonym(name);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SYNONYM_QUERY)) {
                statement.setString(1, name.toUpperCase());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        synonym.setPublicSynonym(false);
                        synonym.setOwner(resultSet.getString("TABLE_OWNER"));
                        synonym.setNameObject(resultSet.getString("TABLE_NAME"));
                    }
                }
            }
        }
        return synonym;
    }

}