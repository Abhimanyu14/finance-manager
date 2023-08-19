package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitialDataVersionNumber(
    @SerialName(value = "account")
    val account: Int = 0,

    @SerialName(value = "category")
    val category: Int = 0,

    @SerialName(value = "transaction")
    val transaction: Int = 0,

    @SerialName(value = "transaction_for")
    val transactionFor: Int = 0,
)
