package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import org.junit.Assert
import org.junit.Before
import org.junit.Test

public class TransactionForTest {
    private lateinit var transactionFor: TransactionFor

    @Before
    public fun setUp() {
        transactionFor = TransactionFor(
            title = "Self",
        )
    }

    @Test
    public fun titleToDisplay() {
        Assert.assertEquals(
            transactionFor.title.capitalizeWords(),
            transactionFor.titleToDisplay,
        )
    }
}
