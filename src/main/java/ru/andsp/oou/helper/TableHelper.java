package ru.andsp.oou.helper;

import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.sql.SQLException;

public class TableHelper implements ObjectHelper {
    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        return null;
    }

}
