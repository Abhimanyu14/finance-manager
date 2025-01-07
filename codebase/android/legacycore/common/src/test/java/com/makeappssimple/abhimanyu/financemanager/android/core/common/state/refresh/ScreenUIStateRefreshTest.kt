package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class ScreenUIStateRefreshTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var unconfinedTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region SUT
    private lateinit var screenUIStateRefresh: ScreenUIStateRefresh
    // endregion

    @Test
    fun `when refresh is called, then expect refresh signal event`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        screenUIStateRefresh.refreshSignal.test {
            screenUIStateRefresh.refresh()

            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    private suspend fun setupSUT(
        coroutineScope: CoroutineScope,
    ) {
        screenUIStateRefresh = ScreenUIStateRefreshImpl(
            coroutineScope = coroutineScope,
        )
    }

    // region test setup
    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = runTest(
        timeout = 3.seconds,
        testBody = {
            setUp()
            with(
                receiver = testScope,
            ) {
                block()
            }
            tearDown()
        },
    )

    private suspend fun TestScope.setUp() {
        standardTestDispatcher = StandardTestDispatcher(
            scheduler = testScheduler,
        )
        unconfinedTestDispatcher = UnconfinedTestDispatcher(
            scheduler = testScheduler,
        )
        testScope = TestScope(
            context = standardTestDispatcher,
        )
    }

    private suspend fun TestScope.tearDown() {
    }
    // endregion
}
