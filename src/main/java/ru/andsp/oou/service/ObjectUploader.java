package ru.andsp.oou.service;


import ru.andsp.oou.type.OracleObject;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ObjectUploader {

    private DataSource dataSource;

    private List<OracleObject> objectList;

    private String path;

    public void upload(DataSource dataSource, String path, ProgressCallBack callBack) throws SQLException {
        this.dataSource = dataSource;
        this.path = path;
        objectList = getObjects();
        callBack.setStartCount(objectList.size());
        process(callBack);
    }

    private void process(ProgressCallBack callBack) {
        ExecutorService executorService = Executors.newWorkStealingPool();
        for (OracleObject object : objectList) {
            Task task = new Task(dataSource, object, path, callBack);
            executorService.submit(task);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService = null;
        objectList = null;
    }

    private List<OracleObject> getObjects() throws SQLException {
        ObjectProvider provider = new ObjectProvider();
        return provider.getObjects(dataSource);
    }

}
