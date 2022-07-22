package com.makeappssimple.abhimanyu.financemanager.android.core.database.amount

import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.AmountJson
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.CURRENCY_CODE_INR
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
