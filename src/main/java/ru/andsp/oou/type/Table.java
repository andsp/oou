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
        columnList = new ArrayList<>();
        commentList = new ArrayList<>();
        indexList = new ArrayList<>();
        constraintList = new ArrayList<>();
    }

    public void addColumn(TableColumn column) {
        columnList.add(column);
    }

    public void addColumnComment(TableColumnComment comment) {
        commentList.add(comment);
    }

    public void addIndex(Index index) {
        indexList.add(index);
    }

    public void addConstraint(Constraint constraint) {
        constraintList.add(constraint);
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


    public void setPreserve(boolean preserve) {
        this.preserve = preserve;
        if (preserve) {
            this.temporary = true;
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String getColumnSource() {
        StringBuilder sb = new StringBuilder("(\n");
        for (TableColumn tc : this.columnList) {
            if (sb.length() > 3) {
                sb.append("\n,");
            }
            sb.append(tc.getSource());
        }
        sb.append("\n)");
        return sb.toString();
    }

    private String getColumnCommentSource() {
        StringBuilder sb = new StringBuilder();
        for (TableColumnComment c : commentList) {
            sb.append("\n");
            sb.append(c.getSource());
        }
        return sb.toString();
    }

    private String getIndexSource() {
        StringBuilder sb = new StringBuilder();
        for (Index c : indexList) {
            sb.append("\n");
            sb.append(c.getSource());
        }
        return sb.toString();
    }

    private String getConstraintSource() {
        StringBuilder sb = new StringBuilder();
        for (Constraint c : constraintList) {
            sb.append("\n");
            sb.append(c.getSource());
        }
        return sb.toString();
    }

    @Override
    public String getSource() {
        StringBuilder sb = new StringBuilder(String.format("CREATE TABLE %s\n", name));
        if (columnList != null) {
            sb.append(getColumnSource());
        }
        // temp
        if (temporary && preserve) {
            sb.append("on commit preserve rows");
        }
        if (temporary && !preserve) {
            sb.append("on commit delete rows");
        }
        // end
        sb.append(";");
        // comment
        if (comment != null) {
            sb.append(String.format("\ncomment on table %s is '%s';", name, comment));
        }
        // column comment
        if (!commentList.isEmpty())
            sb.append(getColumnCommentSource());
        // index
        if (!indexList.isEmpty())
            sb.append("\n").append(getIndexSource());
        if (!constraintList.isEmpty())
            sb.append("\n").append(getConstraintSource());

        return sb.toString();
    }


}