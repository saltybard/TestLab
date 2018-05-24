package edu.tacoma.uw.renzc.testinglab;

import org.junit.Test;

import edu.tacoma.uw.renzc.testinglab.Account;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class AccountTest {


    @Test
    public void testAccountConstructor() {
        assertNotNull(new Account("mmuppa@uw.edu", "test1@3"));
    }
    @Test
    public void testAccountConstructorBadEmail() {
        try {
            new Account("mmuppauw.edu", "test1@3");
            fail("Account created with invalid email");
        } catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void testAccountSetBadEmail() {
        try {
            Account test = new Account("mmuppauw.edu", "test1@3");
            test.setEmail("renzc");
            fail("Account set with invalid email");
        } catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void testAccountSetGoodEmail() {
        try {
            Account test = new Account("mmuppauw.edu", "test1@3");
            test.setEmail("renzc@uw.edu");
            String emailTest = test.getEmail();
            assertEquals("renzc@uw.edu", emailTest);
            fail("Account email not updating properly");
        } catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void testAccountSetBadPassword() {
        try {
            Account test = new Account("mmuppauw.edu", "test1@3");
            test.setPassword("te");
            fail("Account set with invalid password");
        } catch(IllegalArgumentException e) {

        }
    }

    @Test
    public void testAccountSetGoodPassword() {
        try {
            Account test = new Account("mmuppauw.edu", "test1@3");
            test.setPassword("testing1!1");
            String emailTest = test.getPassword();
            assertEquals("testing1!1", emailTest);
            fail("Account password not updating properly");
        } catch(IllegalArgumentException e) {

        }
    }

}
