package ru.andsp.oou.type;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TypeTest {

    private Type type;

    @Before
    public void setUp() throws Exception {
        type = new Type("test");
    }

    @Test
    public void testSetSource() throws Exception {
        type.setSource("test");
        assertEquals("Не верное отображение исходников", type.getSource(), "test");
    }
}