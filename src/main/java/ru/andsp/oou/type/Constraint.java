package ru.andsp.oou.type;

import java.util.ArrayList;
import java.util.List;

public class Constraint extends OracleObject {

    private String tableName;

    private TypeConstraint constraintType;

    private List<String> columns;

    private String referTableName;

    private List<String> referColumns;

    private boolean deferrable;

    private boolean deferred;

    public Constraint(String name) {
        super(name);
        columns = new ArrayList<>();
        referColumns = new ArrayList<>();
    }

    public void setDeferrable(boolean deferrable) {
        this.deferrable = deferrable;
    }

    public void setDeferred(boolean deferred) {
        this.deferred = deferred;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TypeConstraint getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(TypeConstraint constraintType) {
        this.constraintType = constraintType;
    }

    public void setReferTableName(String referTableName) {
        this.referTableName = referTableName;
    }

    public void addColumn(String column) {
        columns.add(column);
    }

    public void addReferColumn(String referColumn) {
        referColumns.add(referColumn);
    }

    private String getColumns(List<String> cols) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cols.size(); i++) {
            builder.append(cols.get(i));
            if (i + 1 < cols.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @Override
    public String getSource() {
        switch (constraintType) {
            case PRIMARY: {
                return String.format("alter table %s add constraint %s primary key (%s);", tableName, name, getColumns(columns));
            }
            case FOREIGN: {
                StringBuilder builder = new StringBuilder();
                builder.append(String.format("alter table %s add constraint %s foreign key (%s) references %s (%s)", tableName, name, getColumns(columns), referTableName, getColumns(referColumns)));
                if (deferrable) {
                    builder.append(" deferrable");
                    if (deferred) {
                        builder.append(" initially deferred");
                    }
                }
                builder.append(";");
                return builder.toString();
            }
            case UNIQUE: {
                return String.format("alter table %s add constraint %s unique (%s);", tableName, name, getColumns(columns));
            }
            default:
                return null;
        }
    }
}