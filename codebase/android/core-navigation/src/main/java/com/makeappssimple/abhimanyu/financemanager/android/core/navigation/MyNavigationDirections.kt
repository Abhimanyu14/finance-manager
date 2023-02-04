package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

object NavArgs {
    const val WEBPAGE_URL = "webpageUrl"
    const val CATEGORY_ID = "categoryId"
    const val SOURCE_ID = "sourceId"
    const val TRANSACTION_ID = "transactionId"
    const val EDIT = "edit"
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

    fun addTransaction(
        transactionId: Int?,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.AddTransaction.route}/${transactionId}"
            override val screen = Screen.AddTransaction.route
        }
    }

    fun addTransactionFor(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.AddTransactionFor.route
            override val screen = Screen.AddTransactionFor.route
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

    fun viewTransaction(
        transactionId: Int,
    ): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = "${Screen.ViewTransaction.route}/${transactionId}"
            override val screen = Screen.ViewTransaction.route
        }
    }

    fun viewTransactionFor(): NavigationCommand {
        return object : NavigationCommand {
            override val command = Command.NAVIGATE
            override val destination = Screen.ViewTransactionFor.route
            override val screen = Screen.ViewTransactionFor.route
        }
    }
}
