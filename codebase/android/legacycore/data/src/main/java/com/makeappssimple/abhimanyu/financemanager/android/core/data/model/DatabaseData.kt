package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DatabaseData(
    val categories: List<Category> = listOf(),

    val accounts: List<Account> = listOf(),

    @SerialName(value = "transaction_for_values")
    val transactionForValues: List<TransactionFor> = listOf(),

    val transactions: List<Transaction> = listOf(),
)
