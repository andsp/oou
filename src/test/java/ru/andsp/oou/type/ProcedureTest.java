package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ProcedureTest {

    private Procedure procedure;

    @Before
    public void setUp() throws Exception {
        procedure = new Procedure("test");
    }

    @Test
    public void testSetSource() throws Exception {
        procedure.setSource("test");
        assertEquals("Не верное отображение исходников", procedure.getSource(), "test");
    }
}