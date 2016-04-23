package ru.andsp.oou.helper;

import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.type.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConstraintHelper implements ObjectHelper {

    private static final String PRIMARY = "P";
    private static final String FOREIGN = "R";
    private static final String UNIQUE = "U";

    private static final String CONSTRAINT_QUERY = "select c.constraint_name,\n" +
            "       c.table_name,\n" +
            "       c.constraint_type,\n" +
            "       c.deferrable,\n" +
            "       c.deferred,\n" +
            "       rc.table_name as refer_table_name,\n" +
            "       rc.constraint_name as refer_name\n" +
            "  from user_constraints c\n" +
            "  left join user_constraints rc\n" +
            "    on rc.constraint_name = c.r_constraint_name\n" +
            " where c.constraint_type in ('P', 'R', 'U')\n" +
            "   and c.table_name = ?\n" +
            " order by  c.constraint_name ";

    private static final String CONSTRAINT_COLUMN_QUERY = "select c.column_name\n" +
            "  from user_cons_columns c\n" +
            " where c.constraint_name = ?\n" +
            " order by c.position asc";


    private void constraintColumnLoad(Connection connection, Constraint constraint, String name, boolean isRefer) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CONSTRAINT_COLUMN_QUERY)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (isRefer) {
                        constraint.addReferColumn(resultSet.getString("COLUMN_NAME"));
                    } else {
                        constraint.addColumn(resultSet.getString("COLUMN_NAME"));
                    }
                }
            }
        }
    }

    public void loadFromTable(Connection connection, Table table) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CONSTRAINT_QUERY)) {
            statement.setString(1, table.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Constraint constraint = new Constraint(resultSet.getString("constraint_name"));
                    String typeConstraint = resultSet.getString("constraint_type");
                    switch (typeConstraint) {
                        case FOREIGN: {
                            constraint.setConstraintType(TypeConstraint.FOREIGN);
                            break;
                        }
                        case PRIMARY: {
                            constraint.setConstraintType(TypeConstraint.PRIMARY);
                            break;
                        }
                        case UNIQUE: {
                            constraint.setConstraintType(TypeConstraint.UNIQUE);
                            break;
                        }
                    }
                    constraint.setTableName(table.getName());
                    constraintColumnLoad(connection, constraint, constraint.getName(), false);
                    if (constraint.getConstraintType().equals(TypeConstraint.FOREIGN)) {
                        constraintColumnLoad(connection, constraint, resultSet.getString("refer_name"), true);
                        constraint.setReferTableName(resultSet.getString("refer_table_name"));
                        constraint.setDeferrable(resultSet.getString("deferrable").equals("DEFERRABLE"));
                        constraint.setDeferred(resultSet.getString("deferred").equals("DEFERRED"));
                    }
                    table.addConstraint(constraint);
                }
            }
        }
    }


    @Override
    public OracleObject load(DataSource dataSource, TypeObject type, String name) throws SQLException {
        throw new NotImplementedException();
    }
}
