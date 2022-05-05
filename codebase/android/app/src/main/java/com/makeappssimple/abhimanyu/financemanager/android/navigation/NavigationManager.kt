package com.makeappssimple.abhimanyu.financemanager.android.navigation

import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavigationDirections.default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface NavigationManager {
    val command: StateFlow<NavigationCommand>

    fun navigate(
        navigationCommand: NavigationCommand,
    )
}

class NavigationManagerImpl : NavigationManager {
    private var _command: MutableStateFlow<NavigationCommand> = MutableStateFlow(
        value = default(),
    )
    override val command: StateFlow<NavigationCommand> = _command

    override fun navigate(
        navigationCommand: NavigationCommand,
    ) {
        _command.value = navigationCommand
    }
}
