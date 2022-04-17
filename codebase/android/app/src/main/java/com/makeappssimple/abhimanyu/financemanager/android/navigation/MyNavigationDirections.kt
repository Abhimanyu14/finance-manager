package com.makeappssimple.abhimanyu.financemanager.android.navigation

object NavArgs {
    const val WEBPAGE_URL = "webpageUrl"
    const val SOURCE_ID = "sourceId"
}

object MyNavigationDirections {

    // Default
    fun default(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NOOP
            override val destination = ""
            override val screen = ""
        }
    }

    // Navigate up
    fun navigateUp(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE_UP
            override val destination = ""
            override val screen = ""
        }
    }

    // Clear backstack
    fun clearBackstack(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
            override val destination = ""
            override val screen = ""
        }
    }

    // Clear till root
    fun clearTillRoot(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.CLEAR_TILL_ROOT
            override val destination = ""
            override val screen = ""
        }
    }

    // App specific
    fun addCategory(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.AddCategory.route
            override val screen = Screen.AddCategory.route
        }
    }

    fun addSource(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.AddSource.route
            override val screen = Screen.AddSource.route
        }
    }

    fun addTransaction(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.AddTransaction.route
            override val screen = Screen.AddTransaction.route
        }
    }

    fun balanceDetails(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.BalanceDetails.route
            override val screen = Screen.BalanceDetails.route
        }
    }

    fun categories(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Categories.route
            override val screen = Screen.Categories.route
        }
    }

    fun categoryDetails(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.CategoryDetails.route
            override val screen = Screen.CategoryDetails.route
        }
    }

    fun home(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Home.route
            override val screen = Screen.Home.route
        }
    }

    fun settings(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Settings.route
            override val screen = Screen.Settings.route
        }
    }

    fun sourceDetails(
        sourceId: Int,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.SourceDetails.route}/${sourceId}"
            override val screen = Screen.SourceDetails.route
        }
    }

    fun sources(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Sources.route
            override val screen = Screen.Sources.route
        }
    }
}
