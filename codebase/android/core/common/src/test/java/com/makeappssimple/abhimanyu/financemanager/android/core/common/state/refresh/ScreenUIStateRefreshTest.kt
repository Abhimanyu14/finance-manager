package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh

import app.cash.turbine.test
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class ScreenUIStateRefreshTest {
    // region testing
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region SUT
    private lateinit var screenUIStateRefresh: ScreenUIStateRefresh
    // endregion

    // region test setup
    @Before
    fun setUp() = runTest {
        testDispatcher = UnconfinedTestDispatcher(
            scheduler = testScheduler,
        )
        testScope = TestScope(
            context = testDispatcher,
        )

        screenUIStateRefresh = ScreenUIStateRefreshImpl(
            coroutineScope = testScope,
        )
    }

    @After
    fun tearDown() = runTest {
    }
    // endregion

    @Test
    fun refresh() = runTestWithTimeout {
        screenUIStateRefresh.refreshSignal.test {
            screenUIStateRefresh.refresh()

            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = runTest(
        timeout = 3.seconds,
        testBody = block,
    )
}
