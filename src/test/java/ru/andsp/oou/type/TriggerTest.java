package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TriggerTest {

    private Trigger trigger;

    @Before
    public void setUp() throws Exception {
        trigger = new Trigger("test");
    }

    @Test
    public void testSetSource() throws Exception {
        trigger.setSource("test");
        assertEquals("Не верное отображение исходников",trigger.getSource(),"test");
    }
}