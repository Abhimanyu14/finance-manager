package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Command
import com.makeappssimple.abhimanyu.financemanager.android.viewmodel.MainActivityViewModel

@Composable
internal fun MyNavGraph(
    activityViewModel: MainActivityViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val myLogger = LocalMyLogger.current
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
            activityViewModel.navigator.command.collect { command ->
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
