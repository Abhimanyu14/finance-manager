package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.CurrencyCodeConstants
import org.junit.Assert
import org.junit.Test

internal class AmountTest {
    @Test
    fun toNonSignedString_valueIsPositive() {
        val result = testAmount1.toNonSignedString()

        Assert.assertEquals(
            "₹23",
            result,
        )
    }

    @Test
    fun toNonSignedString_valueIsNegative() {
        val result = testAmount3.toNonSignedString()

        Assert.assertEquals(
            "₹31",
            result,
        )
    }

    @Test
    fun toNonSignedString_valueIsZero() {
        val result = testAmount4.toNonSignedString()

        Assert.assertEquals(
            "₹0",
            result,
        )
    }

    @Test
    fun toSignedString_valueIsPositive() {
        val result = testAmount1.toSignedString(
            isPositive = true,
        )

        Assert.assertEquals(
            "+ ₹23",
            result,
        )
    }

    @Test
    fun toSignedString_valueIsNegative() {
        val result = testAmount3.toSignedString(
            isNegative = true,
        )

        Assert.assertEquals(
            "- ₹31",
            result,
        )
    }

    @Test
    fun toSignedString_valueIsZero() {
        val result = testAmount4.toSignedString()

        Assert.assertEquals(
            "₹0",
            result,
        )
    }

    @Test
    fun toString_valueIsNonNegative() {
        val result = testAmount1.toString()

        Assert.assertEquals(
            "₹23",
            result,
        )
    }

    @Test
    fun toString_valueIsNegative() {
        val result = testAmount3.toString()

        Assert.assertEquals(
            "- ₹31",
            result,
        )
    }

    @Test
    fun plus() {
        val result = testAmount1 + testAmount2

        Assert.assertEquals(
            50,
            result.value,
        )
        Assert.assertEquals(
            CurrencyCodeConstants.INR,
            result.currency.currencyCode,
        )
    }

    @Test
    fun minus() {
        val result = testAmount1 - testAmount2

        Assert.assertEquals(
            -4,
            result.value,
        )
        Assert.assertEquals(
            CurrencyCodeConstants.INR,
            result.currency.currencyCode,
        )
    }

    companion object {
        private var testAmount1: AmountEntity = AmountEntity(
            value = 23,
        )
        private var testAmount2: AmountEntity = AmountEntity(
            value = 27,
        )
        private var testAmount3: AmountEntity = AmountEntity(
            value = -31,
        )
        private var testAmount4: AmountEntity = AmountEntity(
            value = 0,
        )
    }
}
