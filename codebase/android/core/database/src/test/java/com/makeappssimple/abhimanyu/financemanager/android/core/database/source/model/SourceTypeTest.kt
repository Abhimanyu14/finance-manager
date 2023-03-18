package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SourceTypeTest {
    private lateinit var sourceType: SourceType

    @Before
    fun setUp() {
    }

    @Test
    fun getSortOrder_cash() {
        sourceType = SourceType.CASH
        Assert.assertEquals(
            1,
            sourceType.sortOrder,
        )
    }

    @Test
    fun getSortOrder_bank() {
        sourceType = SourceType.BANK
        Assert.assertEquals(
            2,
            sourceType.sortOrder,
        )
    }

    @Test
    fun getSortOrder_e_wallet() {
        sourceType = SourceType.E_WALLET
        Assert.assertEquals(
            3,
            sourceType.sortOrder,
        )
    }
}
