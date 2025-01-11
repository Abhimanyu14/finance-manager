package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

internal class IntExtensionTest {
    // region isNotZero tests
    @Test
    fun isNotZero_positiveInteger() {
        val input = 1
        Assert.assertTrue(input.isNotZero())
    }

    @Test
    fun isNotZero_negativeInteger() {
        val input = -1
        Assert.assertTrue(input.isNotZero())
    }

    @Test
    fun isNotZero_zero() {
        val input = 0
        Assert.assertFalse(input.isNotZero())
    }
    // endregion
}
