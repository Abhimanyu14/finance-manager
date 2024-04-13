package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.model

import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import org.junit.Assert
import org.junit.Before
import org.junit.Test

public class AccountTest {
    private lateinit var account: Account

    @Before
    public fun setUp() {
        account = getTestAccount()
    }

    @Test
    public fun updateBalanceAmount_defaultTest() {
        Assert.assertEquals(
            0L,
            account.balanceAmount.value,
        )

        val result = account.updateBalanceAmount(
            updatedBalanceAmount = 100L,
        )

        Assert.assertEquals(
            0L,
            account.balanceAmount.value,
        )
        Assert.assertEquals(
            100L,
            result.balanceAmount.value,
        )
    }
}
