package com.bank;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BankAccountTest {

    // Test fixture - shared object used across all test methods
    private BankAccount account;

    // ---- Setup ----
    // @Before runs once before EVERY test method
    // This gives each test a fresh BankAccount starting at 10,000 so tests don't affect each other
    @Before
    public void setUp() {
        account = new BankAccount("Tanishi Rai", 10000.0);
        System.out.println("setUp() called - fresh account created with balance 10000.0");
    }

    // ---- Teardown ----
    // @After runs once after EVERY test method, regardless of whether the test passed or failed
    // Good place to clean up resources (close files, DB connections etc.)
    // Here we just log that the test is done
    @After
    public void tearDown() {
        account = null;
        System.out.println("tearDown() called - account set to null");
    }

    // ---- Test 1: Deposit ----
    @Test
    public void testDeposit() {
        // Arrange - define the inputs and expected output
        double depositAmount = 5000.0;
        double expectedBalance = 15000.0;

        // Act - call the method being tested
        account.deposit(depositAmount);

        // Assert - verify the result matches what we expected
        assertEquals(expectedBalance, account.getBalance(), 0.0001);
    }

    // ---- Test 2: Withdrawal ----
    @Test
    public void testWithdraw() {
        // Arrange
        double withdrawAmount = 3000.0;
        double expectedBalance = 7000.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals(expectedBalance, account.getBalance(), 0.0001);
    }

    // ---- Test 3: Withdrawal exceeding balance ----
    @Test
    public void testWithdrawInsufficientBalance() {
        // Arrange
        double withdrawAmount = 99999.0;

        // Act + Assert - when amount exceeds balance, an exception should be thrown
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(withdrawAmount));
    }

    // ---- Test 4: Deposit of zero or negative amount ----
    @Test
    public void testDepositInvalidAmount() {
        // Arrange
        double invalidAmount = -500.0;

        // Act + Assert - negative deposits should be rejected
        assertThrows(IllegalArgumentException.class, () -> account.deposit(invalidAmount));
    }

    // ---- Test 5: Account holder name ----
    @Test
    public void testAccountHolder() {
        // Arrange
        String expectedName = "Tanishi Rai";

        // Act
        String actualName = account.getAccountHolder();

        // Assert
        assertEquals(expectedName, actualName);
    }
}
