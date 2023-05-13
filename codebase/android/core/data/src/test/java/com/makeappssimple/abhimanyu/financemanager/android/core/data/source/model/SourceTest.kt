package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.model

import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
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
