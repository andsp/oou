package ru.andsp.oou.type;


import java.math.BigInteger;

import static org.junit.Assert.*;


public class SequenceTest {

    private Sequence sequence;

    private BigInteger val;


    @org.junit.Before
    public void setUp()throws Exception{
        sequence = new Sequence("test");
        val = new BigInteger("1");
    }



    @org.junit.Test
    public void testSetMax() throws Exception {
        sequence.setMax(val);
        assertEquals("Не установилось максимальное значение", sequence.getMax(), val);
        assertTrue("Не учитывается максимальное значение",sequence.getSource().contains("maxvalue 1"));
    }



    @org.junit.Test
    public void testSetMin() throws Exception {
        sequence.setMin(val);
        assertEquals("Не установилось минимальное значение", sequence.getMin(), val);
        assertTrue("Не учитывается минимальное значение",sequence.getSource().contains("minvalue 1"));
    }



    @org.junit.Test
    public void testSetCicle() throws Exception {
        sequence.setCycle(true);
        assertTrue("Не установилось значение  по кругу", sequence.isCycle());
        assertTrue("Не учитывается значение  по кругу", sequence.getSource().contains("cycle"));
        sequence.setCycle(false);
        assertFalse("Не учитывается значение  по кругу", sequence.getSource().contains("cycle"));
    }



    @org.junit.Test
    public void testSetOrder() throws Exception {
        sequence.setOrder(true);
        assertTrue("Не установилось значение сортировки", sequence.isOrder());
        assertTrue("Не учитывается значение сортировки", sequence.getSource().contains("order"));
        sequence.setOrder(false);
        assertFalse("Не учитывается значение сортировки", sequence.getSource().contains("order"));
    }



    @org.junit.Test
    public void testSetCache() throws Exception {
        sequence.setCache(BigInteger.valueOf(0));
        assertFalse("Не должен учитываться кеш",sequence.getSource().contains("cache"));
        sequence.setCache(val);
        assertEquals("Не установилось кеш", sequence.getCache(), val);
        assertTrue("Не учитывается кеш",sequence.getSource().contains("cache 1"));
    }


    @org.junit.Test
    public void testSetIncrement() throws Exception {
        sequence.setIncrement(val);
        assertEquals("Не установилось инкремент", sequence.getIncrement(), val);
        assertTrue("Не учитывается инкремент",sequence.getSource().contains("increment by 1"));
    }



    @org.junit.Test
    public void testSetStart() throws Exception {
        sequence.setStart(val);
        assertEquals("Не установилось начальное значение", sequence.getStart(), val);
        assertTrue("Не учитывается начальное значение",sequence.getSource().contains("start with 1"));
    }

    @org.junit.Test
    public void testGetSource() throws Exception {
        assertEquals("Неверный текст исходника", sequence.getSource(), "CREATE SEQUENCE test\n;");
    }
}