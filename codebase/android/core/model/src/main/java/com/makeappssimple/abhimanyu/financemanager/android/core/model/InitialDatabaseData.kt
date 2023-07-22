package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class InitialDatabaseData @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("default_accounts")
    @JsonNames("defaultAccounts")
    val defaultAccounts: List<Account>,

    @SerialName("default_transaction_for_values")
    @JsonNames("defaultTransactionForValues")
    val defaultTransactionForValues: List<TransactionFor>,

    @SerialName("default_categories")
    @JsonNames("defaultCategories")
    val defaultCategories: Categories,

    val emojis: Emojis,
)

@Serializable
data class Emojis @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("version_number")
    @JsonNames("versionNumber")
    val versionNumber: Int,

    @SerialName("emojis_data")
    @JsonNames("emojisData")
    val emojisData: List<Emoji>,
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

    val categories: List<Category>,
)
