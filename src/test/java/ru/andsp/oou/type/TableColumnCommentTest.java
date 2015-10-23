package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TableColumnCommentTest {

    private TableColumnComment cc;

    @Before
    public void setUp() throws Exception {
        cc = new TableColumnComment();
    }

    @Test
    public void testSetComment() throws Exception {
        cc.setComment("testSetComment");
        assertTrue("не устанавливается текст коментария", cc.getSource().contains("'testSetComment'"));
    }

    @Test
    public void testSetColName() throws Exception {
        cc.setColName("testSetColName");
        assertTrue("не устанавливается имя поля", cc.getSource().contains(".testSetColName"));
    }

    @Test
    public void testSetTabName() throws Exception {
        cc.setTabName("testSetTabName");
        assertTrue("не устанавливается имя таблицы", cc.getSource().contains("testSetTabName."));
    }

    @Test
    public void testGetSource() throws Exception {
        cc.setComment("Comment");
        cc.setColName("ColName");
        cc.setTabName("TabName");
        assertEquals("не верный текст исходника",cc.getSource(),"comment on column TabName.ColName  is 'Comment';");
    }
}