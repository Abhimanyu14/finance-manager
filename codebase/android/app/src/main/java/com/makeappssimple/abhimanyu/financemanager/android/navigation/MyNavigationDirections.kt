package com.makeappssimple.abhimanyu.financemanager.android.navigation

object NavArgs {
    const val WEBPAGE_URL = "webpageUrl"
    const val CATEGORY_ID = "categoryId"
    const val SOURCE_ID = "sourceId"
    const val TRANSACTION_ID = "transactionId"
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

    fun categories(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Categories.route
            override val screen = Screen.Categories.route
        }
    }

    fun editCategory(
        categoryId: Int,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.EditCategory.route}/${categoryId}"
            override val screen = Screen.EditCategory.route
        }
    }

    fun editSource(
        sourceId: Int,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.EditSource.route}/${sourceId}"
            override val screen = Screen.EditSource.route
        }
    }

    fun editTransaction(
        transactionId: Int,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.EditTransaction.route}/${transactionId}"
            override val screen = Screen.EditTransaction.route
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

    fun sources(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Sources.route
            override val screen = Screen.Sources.route
        }
    }

    fun transactions(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.Transactions.route
            override val screen = Screen.Transactions.route
        }
    }
}
