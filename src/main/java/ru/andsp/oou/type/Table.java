package ru.andsp.oou.type;


import java.util.ArrayList;
import java.util.List;

public class Table extends OracleObject {


    private String comment;

    private boolean temporary;

    private boolean preserve;

    private List<TableColumn> columnList;

    private List<TableColumnComment> commentList;

    private List<Constraint> constraintList;

    private List<Index> indexList;


    public Table(String name) {
        super(name);
        this.typeObject = TypeObject.TABLE;
    }

    public void addColumn(TableColumn column) {
        if (columnList == null) {
            columnList = new ArrayList<>();
        }
        columnList.add(column);
    }

    public void addColumnComment(TableColumnComment comment) {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        commentList.add(comment);
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
        if (!temporary) {
            this.preserve = false;
        }
    }

    public boolean isPreserve() {
        return preserve;
    }

    public void setPreserve(boolean preserve) {
        this.preserve = preserve;
        if (preserve) {
            this.temporary = true;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String getColumnSource() {
        StringBuilder sb = new StringBuilder("\n(");
        for (TableColumn tc : this.columnList) {
            sb.append("\n,");
            sb.append(tc.getSource());
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n)");
        return sb.toString();
    }

    private String getColumnCommentSource() {
        StringBuilder sb = new StringBuilder();
        for (TableColumnComment c : commentList) {
            sb.append(c.getSource());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getSource() {
        StringBuilder sb = new StringBuilder(String.format("CREATE TABLE %s\n", this.name));
        if (this.columnList != null) {
            sb.append(this.getColumnSource());
        }
        // temp
        if (this.temporary && this.preserve) {
            sb.append("on commit preserve rows");
        }
        if (this.temporary && !this.preserve) {
            sb.append("on commit delete rows");
        }
        // end
        sb.append(";");
        // comment
        if (this.comment != null) {
            sb.append(String.format("\ncomment on table %s is '%s';", this.name, this.comment));
        }
        // column comment
        if (this.commentList != null) {
            sb.append("\n").append(this.getColumnCommentSource());
        }
        return sb.toString();
    }


}