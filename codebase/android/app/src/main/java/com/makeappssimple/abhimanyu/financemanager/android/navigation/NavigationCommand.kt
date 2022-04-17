package com.makeappssimple.abhimanyu.financemanager.android.navigation

enum class Command {
    CLEAR_BACKSTACK_AND_NAVIGATE,
    CLEAR_TILL_ROOT,
    NAVIGATE,
    NAVIGATE_UP,
    NOOP,
}

interface NavigationCommand {
    val command: Command
    val destination: String
    val screen: String
}
