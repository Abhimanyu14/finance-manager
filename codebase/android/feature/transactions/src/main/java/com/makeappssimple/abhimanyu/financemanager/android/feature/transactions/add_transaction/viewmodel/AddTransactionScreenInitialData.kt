package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList

public data class AddTransactionScreenInitialData(
    public val defaultAccount: Account,
    public val defaultExpenseCategory: Category,
    public val defaultIncomeCategory: Category,
    public val defaultInvestmentCategory: Category,
    public val accounts: ImmutableList<Account>,
    public val categories: ImmutableList<Category>,
    public val transactionForValues: ImmutableList<TransactionFor>,
    public val validTransactionTypesForNewTransaction: ImmutableList<TransactionType>,
    public val originalTransactionData: TransactionData? = null,
    public val maxRefundAmount: Amount? = null,
)
