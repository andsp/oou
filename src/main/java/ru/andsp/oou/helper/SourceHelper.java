package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.contract.SourceObjectContract;
import ru.andsp.oou.service.OracleObjectFactory;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceHelper implements ObjectHelper {

    private static final String SOURCE_QUERY = "select s.text,case when line=1 and lag(s.line)over(order by s.type, s.line) is not null then 1 else 0 end pr_break from user_source s\n" +
            "where s.name = ?\n" +
            "order by s.type,s.line";


    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE OR REPLACE ");
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SOURCE_QUERY)) {
                statement.setString(1, name.toUpperCase());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("PR_BREAK") == 1) {
                            if(sb.charAt(sb.length()-1)== '\n'){
                                sb.append("/");
                            }else{
                                sb.append("\n/");
                            }
                            sb.append("\nCREATE OR REPLACE ");
                        }
                        sb.append(resultSet.getString("TEXT"));
                    }
                }
            }
        }
        if(sb.charAt(sb.length()-1)== '\n'){
            sb.append("/");
        }else{
            sb.append("\n/");
        }
        SourceObjectContract sourceObject = (SourceObjectContract) OracleObjectFactory.createObject(name, type);
        if (sourceObject != null)
            sourceObject.setSource(sb.toString());
        return (OracleObject) sourceObject;
    }
}