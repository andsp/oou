package ru.andsp.oou.service;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;

import javax.sql.DataSource;
import java.sql.SQLException;


class Task implements Runnable {

    private OracleObject object;

    private final String path;

    private final DataSource dataSource;

    private final ProgressCallBack callBack;

    public Task(DataSource dataSource, OracleObject object, String path, ProgressCallBack callBack) {
        this.object = object;
        this.dataSource = dataSource;
        this.path = path;
        this.callBack = callBack;
    }


    private ObjectHelper getHelper() {
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
            callBack.reportOfFinished(object.getName());
        }
    }
}
