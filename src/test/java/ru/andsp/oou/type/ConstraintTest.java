package ru.andsp.oou.type;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstraintTest {

    @Test
    public void testPrimary() {
        Constraint constraint = new Constraint("test");
        constraint.setConstraintType(TypeConstraint.PRIMARY);
        constraint.setTableName("table_name");
        constraint.addColumn("column");
        assertEquals(constraint.getSource(), "alter table table_name add constraint test primary key (column);");
    }

    @Test
    public void testForeign() {
        Constraint constraint = new Constraint("test");
        constraint.setConstraintType(TypeConstraint.FOREIGN);
        constraint.setTableName("table_name");
        constraint.addColumn("column");
        constraint.setReferTableName("table_2");
        constraint.addReferColumn("column2");
        assertEquals(constraint.getSource(), "alter table table_name add constraint test foreign key (column) references table_2 (column2);");
        constraint.setDeferrable(true);
        assertEquals(constraint.getSource(), "alter table table_name add constraint test foreign key (column) references table_2 (column2) deferrable;");
        constraint.setDeferred(true);
        assertEquals(constraint.getSource(), "alter table table_name add constraint test foreign key (column) references table_2 (column2) deferrable initially deferred;");
        constraint.setDeferrable(false);
        assertEquals(constraint.getSource(), "alter table table_name add constraint test foreign key (column) references table_2 (column2);");
    }

    @Test
    public void testUnique() {
        Constraint constraint = new Constraint("test");
        constraint.setConstraintType(TypeConstraint.UNIQUE);
        constraint.setTableName("table_name");
        constraint.addColumn("column");
        assertEquals(constraint.getSource(), "alter table table_name add constraint test unique (column);");
    }

    @Test
    public void testColumns() {
        Constraint constraint = new Constraint("test");
        constraint.setConstraintType(TypeConstraint.PRIMARY);
        constraint.setTableName("table_name");
        constraint.addColumn("column1");
        constraint.addColumn("column2");
        constraint.addColumn("column3");
        constraint.addColumn("column4");
        assertEquals(constraint.getSource(), "alter table table_name add constraint test primary key (column1,column2,column3,column4);");
    }

}