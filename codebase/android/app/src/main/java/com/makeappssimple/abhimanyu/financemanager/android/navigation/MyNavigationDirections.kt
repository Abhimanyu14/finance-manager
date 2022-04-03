package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.navigation.NamedNavArgument

object NavArgs {
    const val WEBPAGE_URL = "webpageUrl"
}

object MyNavigationDirections {

    // Default
    fun default(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = ""
            override val command = Command.NOOP
        }
    }

    // Navigate up
    fun navigateUp(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = ""
            override val command = Command.NAVIGATE_UP
        }
    }

    // Clear backstack
    fun clearBackstack(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = ""
            override val command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
        }
    }

    // Clear till root
    fun clearTillRoot(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = ""
            override val command = Command.CLEAR_TILL_ROOT
        }
    }

    // App specific
    fun addCategory(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.AddCategory.route
            override val command = Command.NAVIGATE
        }
    }

    fun addSource(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.AddSource.route
            override val command = Command.NAVIGATE
        }
    }

    fun addTransaction(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.AddTransaction.route
            override val command = Command.NAVIGATE
        }
    }

    fun balanceDetails(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.BalanceDetails.route
            override val command = Command.NAVIGATE
        }
    }

    fun categories(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.Categories.route
            override val command = Command.NAVIGATE
        }
    }

    fun categoryDetails(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.CategoryDetails.route
            override val command = Command.NAVIGATE
        }
    }

    fun home(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.Home.route
            override val command = Command.NAVIGATE
        }
    }

    fun settings(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.Settings.route
            override val command = Command.NAVIGATE
        }
    }

    fun sourceDetails(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.SourceDetails.route
            override val command = Command.NAVIGATE
        }
    }

    fun sources(): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = Screen.Sources.route
            override val command = Command.NAVIGATE
        }
    }
}
