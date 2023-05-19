package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitialDataVersionNumber(
    @SerialName(value = "category")
    val category: Int = 0,

    @SerialName(value = "emoji")
    val emoji: Int = 0,

    @SerialName(value = "transaction")
    val transaction: Int = 0,
)
