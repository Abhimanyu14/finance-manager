package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class ScreenUIStateLoadingTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var unconfinedTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region SUT
    private lateinit var screenUIStateLoading: ScreenUIStateLoading
    // endregion

    @Test
    fun `when completeLoading is called and shouldRefresh = true, then expect refresh signal event`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

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
    fun `when completeLoading is called and shouldRefresh = false, then expect no refresh signal event`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

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
    fun `when startLoading is called and shouldRefresh = true, then expect refresh signal event`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

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
    fun `when startLoading is called and shouldRefresh = false, then expect no refresh signal event`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

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

    private suspend fun setupSUT(
        coroutineScope: CoroutineScope,
    ) {
        val screenUIStateRefresh = ScreenUIStateRefreshImpl(
            coroutineScope = coroutineScope,
        )
        screenUIStateLoading = ScreenUIStateLoadingImpl(
            screenUIStateRefresh = screenUIStateRefresh,
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
