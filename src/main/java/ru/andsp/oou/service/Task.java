package ru.andsp.oou.service;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;

import javax.sql.DataSource;
import java.sql.SQLException;


public class Task implements Runnable {

    private OracleObject object;

    private String path;

    private DataSource dataSource;

    public Task(DataSource dataSource, OracleObject object, String path) {
        this.object = object;
        this.dataSource = dataSource;
        this.path = path;
    }


    private ObjectHelper getHelper(){
       return HelperFactory.getHelper(object.getTypeObject());
    }

    @Override
    public void run() {
        try {
            ObjectHelper helper = getHelper();
            object = helper.load(dataSource, object.getTypeObject(), object.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (object != null) {
            Storage.save(object, path);
            System.out.println(object.getName());
        }
    }
}
