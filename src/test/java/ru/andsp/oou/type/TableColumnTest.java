package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TableColumnTest {

    private TableColumn tc;

    @Before
    public void setUp() throws Exception {
        tc = new TableColumn("test");
    }

    @Test
    public void testSetTypes() throws Exception {
        tc.setTypes(DataTypes.DATE);
        assertTrue("не установился тип данных DATE", tc.getSource().contains("DATE".toLowerCase()));
        tc.setTypes(DataTypes.BINARY_DOUBLE);
        assertTrue("не установился тип данных BINARY_DOUBLE", tc.getSource().contains("BINARY_DOUBLE".toLowerCase()));
        tc.setTypes(DataTypes.BINARY_FLOAT);
        assertTrue("не установился тип данных BINARY_FLOAT", tc.getSource().contains("BINARY_FLOAT".toLowerCase()));
        tc.setTypes(DataTypes.BLOB);
        assertTrue("не установился тип данных BLOB", tc.getSource().contains("BLOB".toLowerCase()));
        tc.setTypes(DataTypes.CHAR);
        assertTrue("не установился тип данных CHAR", tc.getSource().contains("CHAR".toLowerCase()));
        tc.setTypes(DataTypes.CLOB);
        assertTrue("не установился тип данных CLOB", tc.getSource().contains("CLOB".toLowerCase()));
        tc.setTypes(DataTypes.LONG);
        assertTrue("не установился тип данных LONG", tc.getSource().contains("LONG".toLowerCase()));
        tc.setTypes(DataTypes.NCLOB);
        assertTrue("не установился тип данных NCLOB", tc.getSource().contains("NCLOB".toLowerCase()));
        tc.setTypes(DataTypes.NUMBER);
        assertTrue("не установился тип данных NUMBER", tc.getSource().contains("NUMBER".toLowerCase()));
        tc.setTypes(DataTypes.NVARCHAR2);
        assertTrue("не установился тип данных NVARCHAR2", tc.getSource().contains("NVARCHAR2".toLowerCase()));
        tc.setTypes(DataTypes.RAW);
        assertTrue("не установился тип данных RAW", tc.getSource().contains("RAW".toLowerCase()));
        tc.setTypes(DataTypes.TIMESTAMP);
        assertTrue("не установился тип данных TIMESTAMP", tc.getSource().contains("TIMESTAMP".toLowerCase()));
        tc.setTypes(DataTypes.VARCHAR2);
        assertTrue("не установился тип данных VARCHAR2", tc.getSource().contains("VARCHAR2".toLowerCase()));
    }

    @Test
    public void testSetLength() throws Exception {
        tc.setTypes(DataTypes.VARCHAR2);
        tc.setLength(10);
        assertEquals("не верно учитывается длинна поля", tc.getSource(), "test varchar2(10)");
    }

    @Test
    public void testSetDefValue() throws Exception {
        tc.setTypes(DataTypes.VARCHAR2);
        tc.setLength(4000);
        tc.setDefValue("testSetDefValue");
        assertTrue(tc.getSource().contains("testSetDefValue"));
    }

    @Test
    public void testSetDecimal() throws Exception {
        tc.setTypes(DataTypes.NUMBER);
        tc.setLength(18);
        tc.setDecimal(2);
        assertEquals("не верно учитывается кол-во десятичной дроби", tc.getSource(), "test number(18,2)");
    }

    @Test
    public void testSetNullable() throws Exception {
        tc.setTypes(DataTypes.NUMBER);
        tc.setNullable(false);
        assertEquals("не верно учитывается not null", tc.getSource(), "test number not null");
    }

    @Test
    public void testGetSource() throws Exception {
        tc.setTypes(DataTypes.CLOB);
        assertEquals(tc.getSource(), "test clob");
    }
}