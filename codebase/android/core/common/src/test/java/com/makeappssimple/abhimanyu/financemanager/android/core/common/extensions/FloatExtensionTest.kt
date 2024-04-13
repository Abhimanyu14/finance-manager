package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

public class FloatExtensionTest {

    // region orZero tests
    @Test
    public fun orZero_floatIsNull() {
        val input: Float? = null

        Assert.assertEquals(
            0F,
            input.orZero(),
        )
    }

    @Test
    public fun orZero_floatIsNotNull() {
        val input = 5F

        Assert.assertEquals(
            input,
            input.orZero(),
        )
    }
    // endregion
}
