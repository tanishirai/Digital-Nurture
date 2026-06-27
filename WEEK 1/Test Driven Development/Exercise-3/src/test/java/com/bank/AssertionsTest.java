package com.bank;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AssertionsTest {

    // The exact example from the exercise sheet - basic assertions on plain values
    @Test
    public void testAssertions() {
        // Assert equals - checks two values are equal
        assertEquals(5, 2 + 3);

        // Assert true - checks a condition evaluates to true
        assertTrue(5 > 3);

        // Assert false - checks a condition evaluates to false
        assertFalse(5 < 3);

        // Assert null - checks a value is null
        assertNull(null);

        // Assert not null - checks a value is not null
        assertNotNull(new Object());
    }

    // Same five assertions, applied to something closer to an actual banking scenario,
    // to see how these get used in a real test instead of just plain values
    @Test
    public void testAccountAssertions() {
        Account account = new Account("Tanishi Rai", 15000.0);

        // Assert equals - checks the balance was set correctly
        assertEquals(15000.0, account.getBalance(), 0.0001);

        // Assert true - checks balance is above a VIP threshold
        assertTrue(account.getBalance() > 10000);

        // Assert false - checks the account is NOT overdrawn
        assertFalse(account.isOverdrawn());

        // Assert null - checks that no nickname was set
        assertNull(account.getNickname(null));

        // Assert not null - checks the account holder's name is present
        assertNotNull(account.getAccountHolder());
    }
}
