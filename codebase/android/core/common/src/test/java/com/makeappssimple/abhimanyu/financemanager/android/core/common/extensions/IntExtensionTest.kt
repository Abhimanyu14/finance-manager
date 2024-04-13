package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

public class IntExtensionTest {

    // region isNotZero tests
    @Test
    public fun isNotZero_positiveInteger() {
        val input = 1
        Assert.assertTrue(input.isNotZero())
    }

    @Test
    public fun isNotZero_negativeInteger() {
        val input = -1
        Assert.assertTrue(input.isNotZero())
    }

    @Test
    public fun isNotZero_zero() {
        val input = 0
        Assert.assertFalse(input.isNotZero())
    }
    // endregion
}
