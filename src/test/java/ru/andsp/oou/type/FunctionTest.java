package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTest {

    private Function function;

    @Before
    public void setUp() throws Exception {
        function = new Function("test");
    }

    @Test
    public void testSetSource() throws Exception {

        function.setSource("test");
        assertEquals("Не верное отображение исходников",function.getSource(),"test");
    }


}