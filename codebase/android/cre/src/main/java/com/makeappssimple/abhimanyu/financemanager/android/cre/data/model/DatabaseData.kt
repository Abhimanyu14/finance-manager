package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
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
