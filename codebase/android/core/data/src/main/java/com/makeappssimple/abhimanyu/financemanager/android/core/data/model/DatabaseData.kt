package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DatabaseData(
    val categories: ImmutableList<Category> = persistentListOf(),

    val accounts: ImmutableList<Account> = persistentListOf(),

    @SerialName(value = "transaction_for_values")
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),

    val transactions: ImmutableList<Transaction> = persistentListOf(),
)
