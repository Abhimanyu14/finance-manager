package com.makeappssimple.abhimanyu.financemanager.android.navigation

import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavigationDirections.default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationManager {
    private var _command: MutableStateFlow<NavigationCommand> = MutableStateFlow(
        value = default(),
    )
    val command: StateFlow<NavigationCommand> = _command

    fun navigate(
        navigationCommand: NavigationCommand,
    ) {
        _command.value = navigationCommand
    }
}
