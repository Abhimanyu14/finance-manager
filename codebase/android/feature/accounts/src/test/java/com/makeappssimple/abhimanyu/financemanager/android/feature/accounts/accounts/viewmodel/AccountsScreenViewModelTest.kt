package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import app.cash.turbine.turbineScope
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore("Fix ViewModel test")
@HiltAndroidTest
internal class AccountsScreenViewModelTest {
    @get:Rule(order = 0)
    internal var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

//    @Inject
//    internal lateinit var accountsScreenViewModel: AccountsScreenViewModel

    /*
    @Before
    internal fun setUp() {
    }
    */

    @Test
    internal fun screenUIDataTest(): TestResult = runTest {
        Assert.assertEquals(
            4,
            2 + 2,
        )

        turbineScope {
//            val receiver = accountsScreenViewModel.screenUIData.testIn(
//                scope = backgroundScope,
//            )
//
//            Assert.assertEquals(
//                MyResult.Loading,
//                receiver.awaitItem(),
//            )
//            receiver.cancel()
        }
    }
}
