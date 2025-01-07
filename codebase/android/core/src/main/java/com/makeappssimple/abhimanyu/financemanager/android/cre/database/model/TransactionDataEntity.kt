package com.makeappssimple.abhimanyu.financemanager.android.cre.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData

public data class TransactionDataEntity(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "account_from_id",
        entityColumn = "id",
    )
    val accountFrom: AccountEntity?,

    @Relation(
        parentColumn = "account_to_id",
        entityColumn = "id",
    )
    val accountTo: AccountEntity?,

    @Relation(
        parentColumn = "transaction_for_id",
        entityColumn = "id",
    )
    val transactionFor: TransactionForEntity,
)

public fun TransactionDataEntity.asExternalModel(): TransactionData {
    return TransactionData(
        transaction = transaction.asExternalModel(),
        category = category?.asExternalModel(),
        accountFrom = accountFrom?.asExternalModel(),
        accountTo = accountTo?.asExternalModel(),
        transactionFor = transactionFor.asExternalModel(),
    )
}
