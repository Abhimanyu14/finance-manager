package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

data class TransactionDataEntity(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "source_from_id",
        entityColumn = "id",
    )
    val sourceFrom: SourceEntity?,

    @Relation(
        parentColumn = "source_to_id",
        entityColumn = "id",
    )
    val sourceTo: SourceEntity?,

    @Relation(
        parentColumn = "transaction_for_id",
        entityColumn = "id",
    )
    val transactionFor: TransactionForEntity,
)

fun TransactionDataEntity.asExternalModel(): TransactionData {
    return TransactionData(
        transaction = transaction.asExternalModel(),
        category = category?.asExternalModel(),
        sourceFrom = sourceFrom?.asExternalModel(),
        sourceTo = sourceTo?.asExternalModel(),
        transactionFor = transactionFor.asExternalModel(),
    )
}
