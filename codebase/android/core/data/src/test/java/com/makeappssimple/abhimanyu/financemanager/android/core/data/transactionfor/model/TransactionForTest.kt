package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
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
