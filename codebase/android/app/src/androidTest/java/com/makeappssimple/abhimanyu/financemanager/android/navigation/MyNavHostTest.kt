package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MyNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navHostController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navHostController = TestNavHostController(
                context = LocalContext.current,
            )
            navHostController.navigatorProvider.addNavigator(
                navigator = ComposeNavigator(),
            )

            MyNavHost(
                navHostController = navHostController,
            )
        }
    }

    @Test
    fun startDestinationIsHome() {
        Assert.assertEquals(
            Screen.Home.route,
            navHostController.currentBackStackEntry?.destination?.route,
        )
//        composeTestRule
//            .onNodeWithContentDescription("Finance Manager")
//            .assertIsDisplayed()
    }
}
