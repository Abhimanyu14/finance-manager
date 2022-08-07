package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestSource
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
    fun getSortOrder() {
        Assert.assertEquals(
            SourceType.CASH,
            source.type,
        )
        Assert.assertEquals(
            1,
            source.type.sortOrder,
        )
    }

    @Test
    fun getSortOrder_bank() {
        source = source
            .copy(
                type = SourceType.BANK,
            )
        Assert.assertEquals(
            2,
            source.type.sortOrder,
        )
    }

    @Test
    fun getSortOrder_e_wallet() {
        source = source
            .copy(
                type = SourceType.E_WALLET,
            )
        Assert.assertEquals(
            3,
            source.type.sortOrder,
        )
    }
}
