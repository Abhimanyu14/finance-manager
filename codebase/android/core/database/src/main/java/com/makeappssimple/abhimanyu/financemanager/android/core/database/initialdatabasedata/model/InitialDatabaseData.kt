package com.makeappssimple.abhimanyu.financemanager.android.core.database.initialdatabasedata.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitialDatabaseData(
    val defaultSources: List<Source>,
    val defaultTransactionForValues: List<TransactionFor>,
    val defaultCategories: Categories,
    val emojis: Emojis,
)

@JsonClass(generateAdapter = true)
data class Emojis(
    val versionNumber: Int,
    val emojisData: List<EmojiLocalEntity>,
)

@JsonClass(generateAdapter = true)
data class Categories(
    val versionNumber: Int,
    val categoriesData: List<CategoriesData>,
)

@JsonClass(generateAdapter = true)
data class CategoriesData(
    val versionNumber: Int,
    val categories: List<Category>,
)
