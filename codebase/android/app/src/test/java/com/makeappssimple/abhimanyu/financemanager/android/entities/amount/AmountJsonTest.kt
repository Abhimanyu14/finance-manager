package com.makeappssimple.abhimanyu.financemanager.android.entities.amount

import org.junit.Test

class AmountJsonTest {
    private lateinit var amountJson: AmountJson

    @Test
    fun initTest() {
        amountJson = AmountJson(
            currency = CURRENCY_CODE_INR,
        )
    }
}
