package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SourceTest {
    private lateinit var source: Source

    @Before
    fun setUp() {
        source = getTestSource()
    }

    @Test
    fun updateBalanceAmount_defaultTest() {
        Assert.assertEquals(
            0L,
            source.balanceAmount.value,
        )

        val result = source.updateBalanceAmount(
            updatedBalanceAmount = 100L,
        )

        Assert.assertEquals(
            0L,
            source.balanceAmount.value,
        )
        Assert.assertEquals(
            100L,
            result.balanceAmount.value,
        )
    }
}
