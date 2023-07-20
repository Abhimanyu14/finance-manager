package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO(Abhi) - Source to Account rename migration
@Entity(tableName = "source_table")
@Serializable
data class SourceEntity @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    @ColumnInfo(name = "balance_amount")
    @SerialName(value = "balance_amount")
    val balanceAmount: AmountEntity = AmountEntity(
        value = 0,
    ),

    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @EncodeDefault
    val type: SourceType = SourceType.CASH,

    val name: String,
)

fun SourceEntity.updateBalanceAmount(
    updatedBalanceAmount: Long,
): SourceEntity {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}

fun SourceEntity.asExternalModel(): Source {
    return Source(
        balanceAmount = balanceAmount.asExternalModel(),
        id = id,
        type = type,
        name = name,
    )
}
