/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author George
 */
public class SetDateTest {
    
    public SetDateTest() {
    }

    @Test
    public void testSomeMethod() {
        SetDate instance = new SetDate();
        String expResult = "12/19/2017 00:00:00";
        String result = instance.inputDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
