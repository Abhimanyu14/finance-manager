package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Transaction(
    val amount: Amount,

    @SerialName(value = "category_id")
    val categoryId: Int? = null,

    val id: Int = 0,

    @SerialName(value = "original_transaction_id")
    val originalTransactionId: Int? = null,

    @SerialName(value = "account_from_id")
    val accountFromId: Int? = null,

    @SerialName(value = "account_to_id")
    val accountToId: Int? = null,

    @SerialName(value = "transaction_for_id")
    val transactionForId: Int = 1,

    @SerialName(value = "refund_transaction_ids")
    val refundTransactionIds: List<Int>? = null,

    @SerialName(value = "creation_timestamp")
    val creationTimestamp: Long,

    @SerialName(value = "transaction_timestamp")
    val transactionTimestamp: Long,

    val description: String = "",

    val title: String,

    @SerialName(value = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)
