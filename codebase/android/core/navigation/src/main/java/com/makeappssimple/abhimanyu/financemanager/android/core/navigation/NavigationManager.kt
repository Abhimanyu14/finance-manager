package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationManager {
    val command: SharedFlow<NavigationCommand>

    fun navigate(
        navigationCommand: NavigationCommand,
    )
}
