package ru.andsp.oou.ui;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.andsp.oou.service.ObjectUploader;
import ru.andsp.oou.service.ProgressCallBack;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Locale;

class UploadInstance {

    private static DataSource getDataSource(Instance instance) {
        HikariConfig config = new HikariConfig();
        String url = String.format("jdbc:oracle:thin:@%s:%s:%s", instance.getHost(), instance.getPort(), instance.getDb());
        config.setJdbcUrl(url);
        config.setUsername(instance.getUser());
        config.setMaximumPoolSize(16);
        config.setPassword(instance.getPass());
        Locale.setDefault(Locale.ENGLISH);
        return new HikariDataSource(config);
    }

    public static synchronized void start(Instance instance, ProgressCallBack callBack) throws SQLException {
        long st = System.currentTimeMillis();
        DataSource dataSource = getDataSource(instance);
        try {
            new ObjectUploader().upload(dataSource, instance.getPath(), callBack);
        } finally {
            if (dataSource != null)
                ((HikariDataSource) dataSource).close();
        }
        System.out.println("time " + (System.currentTimeMillis() - st));
    }
}
