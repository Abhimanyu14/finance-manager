package com.makeappssimple.abhimanyu.financemanager.android.cre.navigation

public object MyNavigationDirections {
    // Default
    public object Default : NavigationCommand {
        override val command: Command = Command.NOOP
        override val destination: String = ""
        override val screen: String = ""
    }

    // Navigate up
    public object NavigateUp : NavigationCommand {
        override val command: Command = Command.NAVIGATE_UP
        override val destination: String = ""
        override val screen: String = ""
    }

    // Clear backstack
    public object ClearBackstack : NavigationCommand {
        override val command: Command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
        override val destination: String = ""
        override val screen: String = ""
    }

    // Clear till root
    public object ClearTillRoot : NavigationCommand {
        override val command: Command = Command.CLEAR_TILL_ROOT
        override val destination: String = ""
        override val screen: String = ""
    }

    // App specific
    public object Accounts : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Accounts.route
        override val screen: String = Screen.Accounts.route
    }

    public object AddAccount : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.AddAccount.route
        override val screen: String = Screen.AddAccount.route
    }

    public data class AddCategory(
        private val transactionType: String,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.AddCategory.route}/${transactionType}"
        override val screen: String = Screen.AddCategory.route
    }

    public data class AddTransaction(
        private val transactionId: Int? = null,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.AddTransaction.route}/${transactionId}"
        override val screen: String = Screen.AddTransaction.route
    }

    public object AddTransactionFor : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.AddTransactionFor.route
        override val screen: String = Screen.AddTransactionFor.route
    }

    public object Analysis : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Analysis.route
        override val screen: String = Screen.Analysis.route
    }

    public object Categories : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Categories.route
        override val screen: String = Screen.Categories.route
    }

    public data class EditAccount(
        private val accountId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.EditAccount.route}/${accountId}"
        override val screen: String = Screen.EditAccount.route
    }

    public data class EditCategory(
        private val categoryId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.EditCategory.route}/${categoryId}"
        override val screen: String = Screen.EditCategory.route
    }

    public data class EditTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.EditTransaction.route}/${transactionId}"
        override val screen: String = Screen.EditTransaction.route
    }

    public data class EditTransactionFor(
        private val transactionForId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.EditTransactionFor.route}/${transactionForId}"
        override val screen: String = Screen.EditTransactionFor.route
    }

    public object Home : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Home.route
        override val screen: String = Screen.Home.route
    }

    public object OpenSourceLicenses : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.OpenSourceLicenses.route
        override val screen: String = Screen.OpenSourceLicenses.route
    }

    public object Settings : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Settings.route
        override val screen: String = Screen.Settings.route
    }

    public object TransactionForValues : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.TransactionForValues.route
        override val screen: String = Screen.TransactionForValues.route
    }

    public object Transactions : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Transactions.route
        override val screen: String = Screen.Transactions.route
    }

    public data class ViewTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = "${Screen.ViewTransaction.route}/${transactionId}"
        override val screen: String = Screen.ViewTransaction.route
    }
}
