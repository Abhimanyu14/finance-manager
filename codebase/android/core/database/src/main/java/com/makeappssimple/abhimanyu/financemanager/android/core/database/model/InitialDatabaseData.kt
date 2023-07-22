package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class InitialDatabaseData(
    @SerialName("default_categories")
    val defaultCategories: Categories,

    @SerialName("default_accounts")
    val defaultAccounts: List<AccountEntity>,

    @SerialName("default_transaction_for_values")
    val defaultTransactionForValues: List<TransactionForEntity>,

    val emojis: Emojis,
)

@Serializable
data class Emojis @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("version_number")
    @JsonNames("versionNumber")
    val versionNumber: Int,

    @SerialName("emojis_data")
    @JsonNames("emojisData")
    val emojisData: List<EmojiEntity>,
)

@Serializable
data class Categories @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("version_number")
    @JsonNames("versionNumber")
    val versionNumber: Int,

    @SerialName("categories_data")
    @JsonNames("categoriesData")
    val categoriesData: List<CategoriesData>,
)

@Serializable
data class CategoriesData @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("version_number")
    @JsonNames("versionNumber")
    val versionNumber: Int,

    val categories: List<CategoryEntity>,
)
