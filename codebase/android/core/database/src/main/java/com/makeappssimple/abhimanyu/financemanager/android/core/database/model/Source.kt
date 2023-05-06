package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "source_table")
@Serializable
data class Source @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    @ColumnInfo(name = "balance_amount")
    @SerialName(value = "balance_amount")
    val balanceAmount: Amount = Amount(
        value = 0,
    ),

    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
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
