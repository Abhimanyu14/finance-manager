package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

public class AnyExtensionTest {

    @Test
    public fun isNull_valueIsNull_returnsTrue() {
        val test: String? = null
        Assert.assertTrue(test.isNull())
    }

    @Test
    public fun isNull_valueIsNotNull_returnsFalse() {
        val test = "Test"
        Assert.assertFalse(test.isNull())
    }

    @Test
    public fun isNotNull_valueIsNull_returnsTrue() {
        val test: String? = null
        Assert.assertFalse(test.isNotNull())
    }

    @Test
    public fun isNotNull_valueIsNotNull_returnsFalse() {
        val test = "Test"
        Assert.assertTrue(test.isNotNull())
    }
}
