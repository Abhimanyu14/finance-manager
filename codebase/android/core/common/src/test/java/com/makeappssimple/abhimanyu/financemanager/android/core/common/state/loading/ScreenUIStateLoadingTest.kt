package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class ScreenUIStateLoadingTest {
    // region testing
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region SUT
    private lateinit var screenUIStateLoading: ScreenUIStateLoading
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

        screenUIStateLoading = ScreenUIStateLoadingImpl(
            screenUIStateRefresh = ScreenUIStateRefreshImpl(
                coroutineScope = testScope,
            ),
        )
    }

    @After
    fun tearDown() = runTest {
    }
    // endregion

    @Test
    fun `completeLoading shouldRefresh = true`() = runTestWithTimeout {
        screenUIStateLoading.refreshSignal.test {
            screenUIStateLoading.completeLoading(
                shouldRefresh = true,
            )

            assertEquals(
                false,
                screenUIStateLoading.isLoading,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `completeLoading shouldRefresh = false`() = runTestWithTimeout {
        screenUIStateLoading.refreshSignal.test {
            screenUIStateLoading.completeLoading(
                shouldRefresh = false,
            )

            assertEquals(
                false,
                screenUIStateLoading.isLoading,
            )
            expectNoEvents()
        }
    }

    @Test
    fun `startLoading shouldRefresh = true`() = runTestWithTimeout {
        screenUIStateLoading.refreshSignal.test {
            screenUIStateLoading.completeLoading(
                shouldRefresh = false,
            )

            screenUIStateLoading.startLoading(
                shouldRefresh = true,
            )

            assertEquals(
                true,
                screenUIStateLoading.isLoading,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `startLoading shouldRefresh = false`() = runTestWithTimeout {
        screenUIStateLoading.refreshSignal.test {
            screenUIStateLoading.completeLoading(
                shouldRefresh = false,
            )

            screenUIStateLoading.startLoading(
                shouldRefresh = false,
            )

            assertEquals(
                true,
                screenUIStateLoading.isLoading,
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
