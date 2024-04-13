package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

public class LongExtensionTest {

    // region orZero tests
    @Test
    public fun orZero_longIsNull() {
        val input: Long? = null

        Assert.assertEquals(
            0L,
            input.orZero(),
        )
    }

    @Test
    public fun orZero_longIsNotNull() {
        val input = 5L

        Assert.assertEquals(
            input,
            input.orZero(),
        )
    }
    // endregion
}
