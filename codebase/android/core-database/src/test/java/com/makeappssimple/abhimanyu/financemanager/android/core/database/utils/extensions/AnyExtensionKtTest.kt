package com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions

import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNull
import org.junit.Assert
import org.junit.Test

class AnyExtensionTest {

    @Test
    fun isNull_valueIsNull_returnsTrue() {
        val test: String? = null
        Assert.assertTrue(test.isNull())
    }

    @Test
    fun isNull_valueIsNotNull_returnsFalse() {
        val test = "Test"
        Assert.assertFalse(test.isNull())
    }

    @Test
    fun isNotNull_valueIsNull_returnsTrue() {
        val test: String? = null
        Assert.assertFalse(test.isNotNull())
    }

    @Test
    fun isNotNull_valueIsNotNull_returnsFalse() {
        val test = "Test"
        Assert.assertTrue(test.isNotNull())
    }
}
