package ru.andsp.oou.ui;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.andsp.oou.service.ObjectUploader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Locale;

public class UploadInstance {

    private static DataSource getDataSource(Instance instance) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:oracle:thin:@%s:%s:%s", instance.getHost(), instance.getPort(), instance.getDb()));
        config.setUsername(instance.getUser());
        config.setPassword(instance.getPass());
        config.setDataSourceClassName("oracle.jdbc.pool.OracleDataSource");
        Locale.setDefault(Locale.ENGLISH);
        return new HikariDataSource(config);
    }

    public static synchronized void start(Instance instance) throws SQLException {
        DataSource dataSource = getDataSource(instance);
        ObjectUploader objectUploader = new ObjectUploader();
        objectUploader.upload(dataSource, instance.getPath());
    }
}
