package ru.andsp.oou.service;


import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ObjectProvider {

    private static final String ALL_OBJECT_QUERY = "select a.object_type,\n" +
            "       a.object_name \n" +
            "  from user_objects a\n" +
            " where a.object_type in (" +
           // "                         'FUNCTION'\n" +
            //"                         ,'PACKAGE'\n" +
            //"                         ,'PROCEDURE'\n" +
            //"                         ,'TYPE'\n" +
            //"                         ,'VIEW'\n" +
            //"                         ,'SEQUENCE'\n" +
            //"                         ,'SYNONYM'\n" +
            "                         'TABLE'\n" +
           // "                         ,'TRIGGER'" +
            ")\n" +
            " order by a.object_type";


    public List<OracleObject> getObjects(DataSource dataSource) throws SQLException {
        List<OracleObject> objects = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(ALL_OBJECT_QUERY)) {
                    while (resultSet.next()) {
                        OracleObject object = OracleObjectFactory.createObject(resultSet.getString("OBJECT_NAME"),
                                TypeObject.valueOf(resultSet.getString("OBJECT_TYPE")));
                        if (object != null) {
                            objects.add(object);
                        }
                    }
                }
            }
        }
        return objects;
    }

}
