package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class DispatcherProviderTest {
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = DispatcherProviderImpl(
            defaultDispatcher = Dispatchers.Default,
            ioDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            mainImmediateDispatcher = Dispatchers.Main.immediate,
            unconfinedDispatcher = Dispatchers.Unconfined,
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
    fun getIo() {
        Assert.assertEquals(
            Dispatchers.IO,
            dispatcherProvider.io,
        )
    }

    @Test
    fun getMain() {
        Assert.assertEquals(
            Dispatchers.Main,
            dispatcherProvider.main,
        )
    }

    @Test
    fun getMainImmediate() {
        Assert.assertEquals(
            Dispatchers.Main.immediate,
            dispatcherProvider.mainImmediate,
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
