package ru.andsp.oou.type;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PackageTest {

    private Package aPackage;

    @Before
    public void setUp() throws Exception {
        aPackage = new Package("test");
    }

    @Test
    public void testSetSource() throws Exception {
        aPackage.setSource("test");
        assertEquals("Не верное отображение исходников",aPackage.getSource(),"test");
    }
}