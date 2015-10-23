package ru.andsp.oou.type;

public class TableColumnComment {

    private String comment;

    private String colName;

    private String tabName;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public TableColumnComment(){}

    public TableColumnComment(String colName,String tabName,String comment){
        this.comment = comment;
        this.colName = colName;
        this.tabName = tabName;
    }

    public String getSource(){
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format("comment on column %s.%s  is '%s';",this.tabName,this.colName,this.comment);
    }
}
