package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model

import androidx.room.Embedded
import androidx.room.Relation
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor

data class TransactionData(
    @Embedded
    val transaction: Transaction,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
    )
    val category: Category?,

    @Relation(
        parentColumn = "source_from_id",
        entityColumn = "id",
    )
    val sourceFrom: Source?,

    @Relation(
        parentColumn = "source_to_id",
        entityColumn = "id",
    )
    val sourceTo: Source?,

    @Relation(
        parentColumn = "transaction_for_id",
        entityColumn = "id",
    )
    val transactionFor: TransactionFor,
)
