package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import org.junit.Assert
import org.junit.Test

public class ScreenTest {
    private lateinit var screen: Screen

    @Test
    public fun accounts() {
        screen = Screen.Accounts
        Assert.assertEquals(
            "accounts",
            screen.route,
        )
    }

    @Test
    public fun addAccount() {
        screen = Screen.AddAccount
        Assert.assertEquals(
            "add_account",
            screen.route,
        )
    }

    @Test
    public fun addCategory() {
        screen = Screen.AddCategory
        Assert.assertEquals(
            "add_category",
            screen.route,
        )
    }

    @Test
    public fun addTransaction() {
        screen = Screen.AddTransaction
        Assert.assertEquals(
            "add_transaction",
            screen.route,
        )
    }

    @Test
    public fun addTransactionFor() {
        screen = Screen.AddTransactionFor
        Assert.assertEquals(
            "add_transaction_for",
            screen.route,
        )
    }

    @Test
    public fun analysis() {
        screen = Screen.Analysis
        Assert.assertEquals(
            "analysis",
            screen.route,
        )
    }

    @Test
    public fun categories() {
        screen = Screen.Categories
        Assert.assertEquals(
            "categories",
            screen.route,
        )
    }

    @Test
    public fun editAccount() {
        screen = Screen.EditAccount
        Assert.assertEquals(
            "edit_account",
            screen.route,
        )
    }

    @Test
    public fun editCategory() {
        screen = Screen.EditCategory
        Assert.assertEquals(
            "edit_category",
            screen.route,
        )
    }

    @Test
    public fun editTransaction() {
        screen = Screen.EditTransaction
        Assert.assertEquals(
            "edit_transaction",
            screen.route,
        )
    }

    @Test
    public fun editTransactionFor() {
        screen = Screen.EditTransactionFor
        Assert.assertEquals(
            "edit_transaction_for",
            screen.route,
        )
    }

    @Test
    public fun home() {
        screen = Screen.Home
        Assert.assertEquals(
            "home",
            screen.route,
        )
    }

    @Test
    public fun openSourceLicenses() {
        screen = Screen.OpenSourceLicenses
        Assert.assertEquals(
            "open_source_licenses",
            screen.route,
        )
    }

    @Test
    public fun settings() {
        screen = Screen.Settings
        Assert.assertEquals(
            "settings",
            screen.route,
        )
    }

    @Test
    public fun transactionForValues() {
        screen = Screen.TransactionForValues
        Assert.assertEquals(
            "transaction_for_values",
            screen.route,
        )
    }

    @Test
    public fun transactions() {
        screen = Screen.Transactions
        Assert.assertEquals(
            "transactions",
            screen.route,
        )
    }

    @Test
    public fun viewTransaction() {
        screen = Screen.ViewTransaction
        Assert.assertEquals(
            "view_transaction",
            screen.route,
        )
    }
}
