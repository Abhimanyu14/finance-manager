package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TransactionForTest {
    private lateinit var transactionFor: TransactionFor

    @Before
    fun setUp() {
        transactionFor = TransactionFor(
            title = "Self",
        )
    }

    @Test
    fun titleToDisplay() {
        Assert.assertEquals(
            transactionFor.title.capitalizeWords(),
            transactionFor.titleToDisplay,
        )
    }
}
