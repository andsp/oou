package ru.andsp.oou.helper;

import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class IndexHelper implements ObjectHelper{


    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}