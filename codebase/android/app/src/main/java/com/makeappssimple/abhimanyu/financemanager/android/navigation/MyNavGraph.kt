package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Command
import com.makeappssimple.abhimanyu.financemanager.android.viewmodel.MainActivityViewModel

@Composable
internal fun MyNavGraph(
    activityViewModel: MainActivityViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val myLogger = LocalLogKit.current
    myLogger.logInfo(
        message = "Inside MyNavGraph",
    )
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(
        key1 = lifecycle,
    ) {
        lifecycle.repeatOnLifecycle(
            state = Lifecycle.State.STARTED,
        ) {
            activityViewModel.navigationKit.command.collect { command ->
                keyboardController?.hide()
                when (command.command) {
                    Command.NAVIGATE -> {
                        navHostController.navigate(
                            route = command.destination,
                        )
                    }

                    Command.NAVIGATE_UP -> {
                        navHostController.navigateUp()
                    }

                    Command.CLEAR_BACKSTACK_AND_NAVIGATE -> {
                        navHostController.navigate(
                            route = command.destination,
                        ) {
                            popUpTo(
                                id = navHostController.graph.findStartDestination().id,
                            ) {
                                inclusive = true
                            }
                        }
                    }

                    Command.CLEAR_TILL_ROOT -> {
                        navHostController.popBackStack(
                            destinationId = navHostController.graph.findStartDestination().id,
                            inclusive = false,
                        )
                    }

                    Command.NOOP -> {}
                }
            }
        }
    }

    MyNavHost(
        navHostController = navHostController,
    )
}
