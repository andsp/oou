package ru.andsp.oou.type;


import java.util.ArrayList;
import java.util.List;

public class Index extends OracleObject {

    private List<String> columns;

    private String tablespace;

    private String tablename;

    private boolean unique;

    private boolean bitmap;


    public void setBitmap(boolean bitmap) {
        this.bitmap = bitmap;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public void addColumn(String column) {
        this.columns.add(column);
    }

    private String getColumns() {
        return null;

    }

    public Index(String name) {
        super(name);
        typeObject = TypeObject.INDEX;
        columns = new ArrayList<>();
        unique = false;
        bitmap = false;
    }

    @Override
    public String getSource() {
        StringBuilder source = new StringBuilder("create ");
        if (unique) {
            source.append("unique");
        } else if (bitmap) {
            source.append("bitmap");
        }
        source.append("index ")
                .append(name)
                .append(" on ")
                .append(tablename)
                .append("(")
                .append(getColumns())
                .append(")");
        if (tablespace != null) {
            source.append(" tablespace ").append(tablespace);
        }
        source.append(";");
        return source.toString();
    }
}
