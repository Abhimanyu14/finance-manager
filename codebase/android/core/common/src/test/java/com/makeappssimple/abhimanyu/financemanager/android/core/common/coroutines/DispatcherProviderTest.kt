package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DefaultDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainImmediateDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.UnconfinedDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

public class DispatcherProviderTest {
    @Inject
    @DefaultDispatcher
    private lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @IoDispatcher
    private lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    @MainDispatcher
    private lateinit var mainDispatcher: CoroutineDispatcher

    @Inject
    @MainImmediateDispatcher
    private lateinit var mainImmediateDispatcher: CoroutineDispatcher

    @Inject
    @UnconfinedDispatcher
    private lateinit var unconfinedDispatcher: CoroutineDispatcher

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    public fun setUp() {
        dispatcherProvider = DispatcherProviderImpl(
            defaultDispatcher = defaultDispatcher,
            ioDispatcher = ioDispatcher,
            mainDispatcher = mainDispatcher,
            mainImmediateDispatcher = mainImmediateDispatcher,
            unconfinedDispatcher = unconfinedDispatcher,
        )
    }

    @Test
    public fun getDefault() {
        Assert.assertEquals(
            Dispatchers.Default,
            dispatcherProvider.default,
        )
    }

    @Test
    public fun getIo() {
        Assert.assertEquals(
            Dispatchers.IO,
            dispatcherProvider.io,
        )
    }

    @Test
    public fun getMain() {
        Assert.assertEquals(
            Dispatchers.Main,
            dispatcherProvider.main,
        )
    }

    @Test
    public fun getMainImmediate() {
        Assert.assertEquals(
            Dispatchers.Main.immediate,
            dispatcherProvider.mainImmediate,
        )
    }

    @Test
    public fun getUnconfined() {
        Assert.assertEquals(
            Dispatchers.Unconfined,
            dispatcherProvider.unconfined,
        )
    }
}
