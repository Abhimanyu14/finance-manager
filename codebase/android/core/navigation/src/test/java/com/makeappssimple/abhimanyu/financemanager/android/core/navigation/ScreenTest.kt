package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import org.junit.Assert
import org.junit.Test

class ScreenTest {
    private lateinit var screen: Screen

    @Test
    fun addCategory() {
        screen = Screen.AddCategory
        Assert.assertEquals(
            "add_category",
            screen.route,
        )
    }

    @Test
    fun addSource() {
        screen = Screen.AddSource
        Assert.assertEquals(
            "add_source",
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
    fun categories() {
        screen = Screen.Categories
        Assert.assertEquals(
            "categories",
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
    fun editSource() {
        screen = Screen.EditSource
        Assert.assertEquals(
            "edit_source",
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
    fun home() {
        screen = Screen.Home
        Assert.assertEquals(
            "home",
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
    fun sources() {
        screen = Screen.Sources
        Assert.assertEquals(
            "sources",
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
}
