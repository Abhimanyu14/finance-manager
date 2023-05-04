package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters

import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestAmount
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AmountConverterTest {
    private lateinit var amountConverter: AmountConverter
    private val testAmount = getTestAmount()
    private val testAmountString = """{"currency":"INR","value":45}"""

    @Before
    fun setUp() {
        amountConverter = AmountConverter()
    }

    @Test
    fun stringToAmount() {
        val result: Amount? = amountConverter.stringToAmount(
            value = testAmountString,
        )

        Assert.assertNotNull(result)
        Assert.assertEquals(
            testAmount.currency.currencyCode,
            result?.currency?.currencyCode,
        )
        Assert.assertEquals(
            testAmount.value,
            result?.value,
        )
    }

    @Test
    fun stringToAmount_valueIsNull() {
        val result: Amount? = amountConverter.stringToAmount(
            value = null,
        )

        Assert.assertNull(result)
    }

    @Test
    fun stringToAmount_valueIsInvalidString() {
        val result: Amount? = amountConverter.stringToAmount(
            value = "23.45",
        )

        Assert.assertNull(result)
    }

    @Test
    fun amountToString() {
        val result: String = amountConverter.amountToString(
            amount = testAmount,
        )

        Assert.assertEquals(
            testAmountString,
            result,
        )
    }

    @Test
    fun amountToString_amountIsNull() {
        val result: String = amountConverter.amountToString(
            amount = null,
        )

        Assert.assertEquals(
            "",
            result,
        )
    }
}
