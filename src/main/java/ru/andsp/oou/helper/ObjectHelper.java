package ru.andsp.oou.helper;

import ru.andsp.oou.type.OracleObject;
import ru.andsp.oou.type.TypeObject;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface ObjectHelper extends AutoCloseable{

    OracleObject load(DataSource dataSource, TypeObject type, String name, String owner) throws SQLException;

}
