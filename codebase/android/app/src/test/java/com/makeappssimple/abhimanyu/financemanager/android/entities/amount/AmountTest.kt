package com.makeappssimple.abhimanyu.financemanager.android.entities.amount

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AmountTest {
    private lateinit var amount: Amount
    private lateinit var amount1: Amount

    @Before
    fun setUp() {
        amount = Amount(
            value = 23,
        )
        amount1 = Amount(
            value = 27,
        )
    }

    @Test
    fun toSignedString() {
        val result = amount.toSignedString()

        Assert.assertEquals(
            "+ ₹23",
            result,
        )
    }

    @Test
    fun toString_defaultTest() {
        val result = amount.toString()

        Assert.assertEquals(
            "₹23",
            result,
        )
    }

    @Test
    fun plus() {
        val result = amount + amount1

        Assert.assertEquals(
            50,
            result.value,
        )
        Assert.assertEquals(
            CURRENCY_CODE_INR,
            result.currency.currencyCode,
        )
    }
}
