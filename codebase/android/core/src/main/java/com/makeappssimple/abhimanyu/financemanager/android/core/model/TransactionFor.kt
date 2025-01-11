package com.makeappssimple.abhimanyu.financemanager.android.core.model

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import kotlinx.serialization.Serializable

@Serializable
public data class TransactionFor(
    val id: Int = 0,

    val title: String,
) {
    val titleToDisplay: String
        get() = title.capitalizeWords()
}
