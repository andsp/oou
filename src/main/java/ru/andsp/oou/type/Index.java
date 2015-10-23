package ru.andsp.oou.type;


import java.util.ArrayList;
import java.util.List;

public class Index extends OracleObject {

    private List<String> columns;

    private String tablespace;

    private String tablename;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTablespace() {
        return tablespace;
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

    Index(String name) {
        super(name);
        this.typeObject = TypeObject.INDEX;
        this.columns = new ArrayList<>();
    }

    @Override
    public String getSource() {
        return String.format("create index %s on %s (%s)  tablespace %s;", this.name,this.tablename, this.getColumns(), this.tablespace);
    }
}
