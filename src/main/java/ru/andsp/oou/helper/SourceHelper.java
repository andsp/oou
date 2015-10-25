package ru.andsp.oou.helper;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.contract.SourceObjectContract;
import ru.andsp.oou.type.*;
import ru.andsp.oou.type.Package;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceHelper implements ObjectHelper {

    private static final String SOURCE_QUERY = "select s.text,case when line=1 and lag(s.line)over(order by s.type, s.line) is not null then 1 else 0 end pr_break from all_source s\n" +
            "where s.name = ?\n" +
            "order by s.type,s.line";

    private PreparedStatement statement;


    private PreparedStatement getStatement(Connection connection, String name) throws SQLException {
        if (this.statement == null) {
            this.statement = connection.prepareStatement(SOURCE_QUERY);
        }
        this.statement.setString(1, name.toUpperCase());
        return this.statement;
    }

    private SourceObjectContract createObect(TypeObject type, String name) {
        switch (type) {
            case TRIGGER:
                return new Trigger(name);
            case TYPE:
                return new Type(name);
            case FUNCTION:
                return new Function(name);
            case PACKAGE:
                return new Package(name);
            case PROCEDURE:
                return new Procedure(name);
            default:
                return null;
        }
    }


    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        StringBuffer sb = new StringBuffer("CREATE OR REPLACE ");
        try (ResultSet resultSet = getStatement(dataSource.getConnection(), name).executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getInt("PR_BREAK")==1){
                    sb.append("\n/\nCREATE OR REPLACE ");
                }
                sb.append(resultSet.getString("TEXT"));
            }
        }
        SourceObjectContract sourceObject = this.createObect(type, name);
        if (sourceObject != null)
            sourceObject.setSource(sb.toString());
        return (OracleObject) sourceObject;
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