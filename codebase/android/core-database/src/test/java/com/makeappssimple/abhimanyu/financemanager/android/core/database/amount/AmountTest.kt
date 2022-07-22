package com.makeappssimple.abhimanyu.financemanager.android.core.database.amount

import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.CURRENCY_CODE_INR
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AmountTest {
    private lateinit var amount: Amount
    private lateinit var amount1: Amount
    private lateinit var amount2: Amount
    private lateinit var amount3: Amount

    @Before
    fun setUp() {
        amount = Amount(
            value = 23,
        )
        amount1 = Amount(
            value = 27,
        )
        amount2 = Amount(
            value = -31,
        )
        amount3 = Amount(
            value = 0,
        )
    }

    @Test
    fun toSignedString_valueIsPositive() {
        val result = amount.toSignedString()

        Assert.assertEquals(
            "+ ₹23",
            result,
        )
    }

    @Test
    fun toSignedString_valueIsNegative() {
        val result = amount2.toSignedString()

        Assert.assertEquals(
            "- ₹31",
            result,
        )
    }

    @Test
    fun toSignedString_valueIsZero() {
        val result = amount3.toSignedString()

        Assert.assertEquals(
            "₹0",
            result,
        )
    }

    @Test
    fun toString_valueIsNonNegative() {
        val result = amount.toString()

        Assert.assertEquals(
            "₹23",
            result,
        )
    }

    @Test
    fun toString_valueIsNegative() {
        val result = amount2.toString()

        Assert.assertEquals(
            "- ₹31",
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
