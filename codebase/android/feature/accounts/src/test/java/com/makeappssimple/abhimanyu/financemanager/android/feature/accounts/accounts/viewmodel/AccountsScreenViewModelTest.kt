package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AccountsScreenViewModelTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var accountsScreenViewModel: AccountsScreenViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun screenUIDataTest() = runTest {
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
