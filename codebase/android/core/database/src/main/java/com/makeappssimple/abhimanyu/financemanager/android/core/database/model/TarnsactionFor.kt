package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "transaction_for_table")
data class TransactionFor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
) {
    val titleToDisplay: String
        get() = title.capitalizeWords()
}
