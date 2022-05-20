package com.makeappssimple.abhimanyu.financemanager.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    val id: Int = 0,
    @Json(name = "parent_category")
    val parentCategoryId: Int? = null,
    @Json(name = "sub_categories")
    val subCategoryIds: List<Int>? = null,
    val description: String = "",
    val emoji: String,
    val title: String,
    @Json(name = "transaction_type")
    val transactionType: TransactionType,
)
