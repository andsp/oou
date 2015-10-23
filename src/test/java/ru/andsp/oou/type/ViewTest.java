package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewTest {

    private View view;

    @Before
    public void setUp() throws Exception {
        view = new View("test");
    }

    @Test
    public void testSetSource() throws Exception {
        view.setSource("test");
        assertEquals("Не верное отображение исходников",view.getSource(),"test");
    }
}