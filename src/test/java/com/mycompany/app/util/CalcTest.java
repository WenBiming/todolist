package com.mycompany.app.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalcTest {
    @Test
    public void testAdd() {

        assertEquals(5, Calc.add(2, 3));
        assertEquals(0, Calc.add(-1, 1));
        assertEquals(-3, Calc.add(-1, -2));
    }
}
