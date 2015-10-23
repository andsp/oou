package ru.andsp.oou.ui.db;


import ru.andsp.oou.ui.Instance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstanceHelper extends DataBaseHelper {


    public InstanceHelper() throws SQLException, ClassNotFoundException {
        super();
    }

    public void remove(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("delete from 'instance' where id=%d", id));
        }
    }

    public List<Instance> getList() throws SQLException {
        List<Instance> instances = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select * from instance")) {
                while (resultSet.next()) {
                    instances.add(new Instance(resultSet.getInt("id"),
                            resultSet.getString("path"),
                            resultSet.getString("host"),
                            resultSet.getString("port"),
                            resultSet.getString("user"),
                            resultSet.getString("pass"),
                            resultSet.getString("db")
                    ));
                }
            }
        }
        return instances;
    }

    public void save(Instance instance) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            if (instance.isNew()) {
                statement.executeUpdate(String.format("insert into instance (path,host,port,user,pass,db)" +
                                " values('%s','%s','%s','%s','%s','%s')",
                        instance.getPath(),
                        instance.getHost(),
                        instance.getPort(),
                        instance.getUser(),
                        instance.getPass(),
                        instance.getDb()));
            } else {
                statement.executeUpdate(String.format("update instance set path='%s',host='%s',port='%s',user='%s',pass='%s',db='%s' where id=%d",
                        instance.getPath(),
                        instance.getHost(),
                        instance.getPort(),
                        instance.getUser(),
                        instance.getPass(),
                        instance.getDb(),
                        instance.getId()));
            }
        }
    }


}
