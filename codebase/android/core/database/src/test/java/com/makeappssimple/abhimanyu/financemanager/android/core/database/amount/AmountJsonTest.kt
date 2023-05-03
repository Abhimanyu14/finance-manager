package com.makeappssimple.abhimanyu.financemanager.android.core.database.amount

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.AmountJson
import org.junit.Test

class AmountJsonTest {
    private lateinit var amountJson: AmountJson

    @Test
    fun initTest() {
        amountJson = AmountJson(
            currency = CurrencyCodeConstants.INR,
        )
    }
}
