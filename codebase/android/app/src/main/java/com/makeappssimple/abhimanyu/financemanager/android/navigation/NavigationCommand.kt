package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.navigation.NamedNavArgument

enum class Command {
    NAVIGATE,
    NAVIGATE_UP,
    CLEAR_BACKSTACK_AND_NAVIGATE,
    CLEAR_TILL_ROOT,
    NOOP,
}

interface NavigationCommand {

    val arguments: List<NamedNavArgument>

    val destination: String

    val command: Command
}
