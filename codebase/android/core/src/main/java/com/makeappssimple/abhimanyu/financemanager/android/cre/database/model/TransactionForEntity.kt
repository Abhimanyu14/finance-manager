package com.makeappssimple.abhimanyu.financemanager.android.cre.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "transaction_for_table")
public data class TransactionForEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
) {
    val titleToDisplay: String
        get() = title.capitalizeWords()
}

public fun TransactionForEntity.asExternalModel(): TransactionFor {
    return TransactionFor(
        id = id,
        title = title,
    )
}
