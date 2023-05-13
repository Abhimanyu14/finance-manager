package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    @SerialName(value = "balance_amount")
    val balanceAmount: Amount = Amount(
        value = 0,
    ),

    @EncodeDefault
    val id: Int = 0,

    @EncodeDefault
    val type: SourceType = SourceType.CASH,

    val name: String,
)

fun Source.updateBalanceAmount(
    updatedBalanceAmount: Long,
): Source {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}
