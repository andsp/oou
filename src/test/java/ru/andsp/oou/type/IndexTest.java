package ru.andsp.oou.type;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IndexTest {

    @Test
    public void testNormal() throws Exception {
        Index index = new Index("test");
        index.setTablename("table_name");
        index.addColumn("col1");
        assertEquals(index.getSource(),"create index test on table_name(col1);");
    }

    @Test
    public void testBitmap() throws Exception {
        Index index = new Index("test");
        index.setTablename("table_name");
        index.addColumn("col1");
        index.setBitmap(true);
        assertEquals(index.getSource(),"create bitmap index test on table_name(col1);");
    }

    @Test
    public void testUnique() throws Exception {
        Index index = new Index("test");
        index.setTablename("table_name");
        index.addColumn("col1");
        index.setUnique(true);
        assertEquals(index.getSource(),"create unique index test on table_name(col1);");
    }

    @Test
    public void testSeveralColumns() throws Exception {
        Index index = new Index("test");
        index.setTablename("table_name");
        index.addColumn("col1");
        index.addColumn("col3");
        index.addColumn("col4");
        index.setUnique(true);
        assertEquals(index.getSource(),"create unique index test on table_name(col1,col3,col4);");
    }


}