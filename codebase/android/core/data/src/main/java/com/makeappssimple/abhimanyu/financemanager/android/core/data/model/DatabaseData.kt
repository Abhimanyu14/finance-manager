package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DatabaseData(
    val categories: List<Category> = emptyList(),

    val accounts: List<Account> = emptyList(),

    @SerialName(value = "transaction_for_values")
    val transactionForValues: List<TransactionFor> = emptyList(),

    val transactions: List<Transaction> = emptyList(),
)
