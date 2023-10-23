package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import org.junit.Assert
import org.junit.Test

class ScreenTest {
    private lateinit var screen: Screen

    @Test
    fun accounts() {
        screen = Screen.Accounts
        Assert.assertEquals(
            "accounts",
            screen.route,
        )
    }

    @Test
    fun addAccount() {
        screen = Screen.AddAccount
        Assert.assertEquals(
            "add_account",
            screen.route,
        )
    }

    @Test
    fun addCategory() {
        screen = Screen.AddCategory
        Assert.assertEquals(
            "add_category",
            screen.route,
        )
    }

    @Test
    fun addTransaction() {
        screen = Screen.AddTransaction
        Assert.assertEquals(
            "add_transaction",
            screen.route,
        )
    }

    @Test
    fun addTransactionFor() {
        screen = Screen.AddTransactionFor
        Assert.assertEquals(
            "add_transaction_for",
            screen.route,
        )
    }

    @Test
    fun analysis() {
        screen = Screen.Analysis
        Assert.assertEquals(
            "analysis",
            screen.route,
        )
    }

    @Test
    fun categories() {
        screen = Screen.Categories
        Assert.assertEquals(
            "categories",
            screen.route,
        )
    }

    @Test
    fun editAccount() {
        screen = Screen.EditAccount
        Assert.assertEquals(
            "edit_account",
            screen.route,
        )
    }

    @Test
    fun editCategory() {
        screen = Screen.EditCategory
        Assert.assertEquals(
            "edit_category",
            screen.route,
        )
    }

    @Test
    fun editTransaction() {
        screen = Screen.EditTransaction
        Assert.assertEquals(
            "edit_transaction",
            screen.route,
        )
    }

    @Test
    fun editTransactionFor() {
        screen = Screen.EditTransactionFor
        Assert.assertEquals(
            "edit_transaction_for",
            screen.route,
        )
    }

    @Test
    fun home() {
        screen = Screen.Home
        Assert.assertEquals(
            "home",
            screen.route,
        )
    }

    @Test
    fun openSourceLicenses() {
        screen = Screen.OpenSourceLicenses
        Assert.assertEquals(
            "open_source_licenses",
            screen.route,
        )
    }

    @Test
    fun settings() {
        screen = Screen.Settings
        Assert.assertEquals(
            "settings",
            screen.route,
        )
    }

    @Test
    fun transactionForValues() {
        screen = Screen.TransactionForValues
        Assert.assertEquals(
            "transaction_for_values",
            screen.route,
        )
    }

    @Test
    fun transactions() {
        screen = Screen.Transactions
        Assert.assertEquals(
            "transactions",
            screen.route,
        )
    }

    @Test
    fun viewTransaction() {
        screen = Screen.ViewTransaction
        Assert.assertEquals(
            "view_transaction",
            screen.route,
        )
    }
}
