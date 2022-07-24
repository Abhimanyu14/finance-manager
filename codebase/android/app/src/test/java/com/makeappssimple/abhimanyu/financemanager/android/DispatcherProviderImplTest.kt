package com.makeappssimple.abhimanyu.financemanager.android

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProviderImpl
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DispatcherProviderImplTest {
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = DispatcherProviderImpl()
    }

    @Test
    fun getMain() {
        Assert.assertEquals(
            Dispatchers.Main,
            dispatcherProvider.main,
        )
    }

    @Test
    fun getIo() {
        Assert.assertEquals(
            Dispatchers.IO,
            dispatcherProvider.io,
        )
    }

    @Test
    fun getDefault() {
        Assert.assertEquals(
            Dispatchers.Default,
            dispatcherProvider.default,
        )
    }

    @Test
    fun getUnconfined() {
        Assert.assertEquals(
            Dispatchers.Unconfined,
            dispatcherProvider.unconfined,
        )
    }
}
