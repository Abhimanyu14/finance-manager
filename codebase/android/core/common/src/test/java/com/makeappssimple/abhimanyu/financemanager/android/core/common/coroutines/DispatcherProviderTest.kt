package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DispatcherProviderTest {
//    @Inject
//    @DefaultDispatcher
//    private lateinit var defaultDispatcher: CoroutineDispatcher
//
//    @Inject
//    @IoDispatcher
//    private lateinit var ioDispatcher: CoroutineDispatcher
//
//    @Inject
//    @MainDispatcher
//    private lateinit var mainDispatcher: CoroutineDispatcher
//
//    @Inject
//    @MainImmediateDispatcher
//    private lateinit var mainImmediateDispatcher: CoroutineDispatcher
//
//    @Inject
//    @UnconfinedDispatcher
//    private lateinit var unconfinedDispatcher: CoroutineDispatcher

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = DispatcherProviderImpl(
//            defaultDispatcher = defaultDispatcher,
//            ioDispatcher = ioDispatcher,
//            mainDispatcher = mainDispatcher,
//            mainImmediateDispatcher = mainImmediateDispatcher,
//            unconfinedDispatcher = unconfinedDispatcher,
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
