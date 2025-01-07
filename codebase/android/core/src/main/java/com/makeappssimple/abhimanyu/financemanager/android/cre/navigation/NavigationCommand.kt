package com.makeappssimple.abhimanyu.financemanager.android.cre.navigation

public enum class Command {
    CLEAR_BACKSTACK_AND_NAVIGATE,
    CLEAR_TILL_ROOT,
    NAVIGATE,
    NAVIGATE_UP,
    NOOP,
}

public interface NavigationCommand {
    public val command: Command
    public val destination: String
    public val screen: String
}
