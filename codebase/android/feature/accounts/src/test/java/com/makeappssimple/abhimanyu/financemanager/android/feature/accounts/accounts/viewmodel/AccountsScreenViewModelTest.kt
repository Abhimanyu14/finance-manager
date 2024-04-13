package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
public class AccountsScreenViewModelTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    public lateinit var accountsScreenViewModel: AccountsScreenViewModel

    @Before
    public fun setUp() {
    }

    @Test
    public fun screenUIDataTest(): TestResult = runTest {
        Assert.assertEquals(
            4,
            2 + 2,
        )

        turbineScope {
            val receiver = accountsScreenViewModel.screenUIData.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyResult.Loading,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }
}
