package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TableTest {

    private Table table;

    @Before
    public void setUp() throws Exception {
        table = new Table("test");
    }

    @Test
    public void testSetComment() throws Exception {
        table.setComment("test");
        assertTrue("Не учитывается комментарий", table.getSource().contains("comment on table test is 'test';"));
    }

    @Test
    public void testGetSource() throws Exception {
        assertEquals("Неверный текст исходника", table.getSource(), "CREATE TABLE test\n" +
                "(\n" +
                "\n" +
                ");");
        table.addColumnComment(new TableColumnComment("colName", "tabName", "comment"));
        assertEquals("Неверный текст исходника", table.getSource(), "CREATE TABLE test\n" +
                "(\n" +
                "\n" +
                ");\n" +
                "comment on column tabName.colName  is 'comment';");
    }
}