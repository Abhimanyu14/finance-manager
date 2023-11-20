package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

object MyNavigationDirections {
    // Default
    object Default : NavigationCommand {
        override val command = Command.NOOP
        override val destination = ""
        override val screen = ""
    }

    // Navigate up
    object NavigateUp : NavigationCommand {
        override val command = Command.NAVIGATE_UP
        override val destination = ""
        override val screen = ""
    }

    // Clear backstack
    object ClearBackstack : NavigationCommand {
        override val command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
        override val destination = ""
        override val screen = ""
    }

    // Clear till root
    object ClearTillRoot : NavigationCommand {
        override val command = Command.CLEAR_TILL_ROOT
        override val destination = ""
        override val screen = ""
    }

    // App specific
    object Accounts : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Accounts.route
        override val screen = Screen.Accounts.route
    }

    object AddAccount : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.AddAccount.route
        override val screen = Screen.AddAccount.route
    }

    data class AddCategory(
        private val transactionType: String,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.AddCategory.route}/${transactionType}"
        override val screen = Screen.AddCategory.route
    }

    data class AddTransaction(
        private val transactionId: Int? = null,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.AddTransaction.route}/${transactionId}"
        override val screen = Screen.AddTransaction.route
    }

    object AddTransactionFor : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.AddTransactionFor.route
        override val screen = Screen.AddTransactionFor.route
    }

    object Analysis : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Analysis.route
        override val screen = Screen.Analysis.route
    }

    object Categories : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Categories.route
        override val screen = Screen.Categories.route
    }

    data class EditAccount(
        private val accountId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.EditAccount.route}/${accountId}"
        override val screen = Screen.EditAccount.route
    }

    data class EditCategory(
        private val categoryId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.EditCategory.route}/${categoryId}"
        override val screen = Screen.EditCategory.route
    }

    data class EditTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.EditTransaction.route}/${transactionId}"
        override val screen = Screen.EditTransaction.route
    }

    data class EditTransactionFor(
        private val transactionForId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.EditTransactionFor.route}/${transactionForId}"
        override val screen = Screen.EditTransactionFor.route
    }

    object Home : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Home.route
        override val screen = Screen.Home.route
    }

    object OpenSourceLicenses : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.OpenSourceLicenses.route
        override val screen = Screen.OpenSourceLicenses.route
    }

    object Settings : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Settings.route
        override val screen = Screen.Settings.route
    }

    object TransactionForValues : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.TransactionForValues.route
        override val screen = Screen.TransactionForValues.route
    }

    object Transactions : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Transactions.route
        override val screen = Screen.Transactions.route
    }

    data class ViewTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.ViewTransaction.route}/${transactionId}"
        override val screen = Screen.ViewTransaction.route
    }
}
