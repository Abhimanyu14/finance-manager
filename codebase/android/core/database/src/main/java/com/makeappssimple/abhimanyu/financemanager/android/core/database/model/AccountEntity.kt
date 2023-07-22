package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "account_table")
@Serializable
data class AccountEntity @OptIn(ExperimentalSerializationApi::class) constructor(
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
    val type: AccountType = AccountType.CASH,

    val name: String,
)

fun AccountEntity.updateBalanceAmount(
    updatedBalanceAmount: Long,
): AccountEntity {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}

fun AccountEntity.asExternalModel(): Account {
    return Account(
        balanceAmount = balanceAmount.asExternalModel(),
        id = id,
        type = type,
        name = name,
    )
}
