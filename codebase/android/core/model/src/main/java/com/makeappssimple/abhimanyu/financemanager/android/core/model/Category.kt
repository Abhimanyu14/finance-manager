package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
public data class Category(
    @EncodeDefault
    val id: Int = 0,

    @SerialName(value = "parent_category")
    val parentCategoryId: Int? = null,

    @SerialName(value = "sub_categories")
    val subCategoryIds: List<Int>? = null,

    @EncodeDefault
    val description: String = "",

    val emoji: String,

    val title: String,

    @SerialName(value = "transaction_type")
    @JsonNames("transactionType")
    val transactionType: TransactionType,
)
