package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation

import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.MyNavigationDirections.default
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {
    var command = MutableStateFlow(
        value = default(),
    )

    fun navigate(
        direction: NavigationCommand,
    ) {
        command.value = direction
    }
}
