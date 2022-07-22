package com.makeappssimple.abhimanyu.financemanager.android.core.database.initialdatabasedata.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitialDatabaseData(
    val defaultCategories: List<Category>,
    val defaultSources: List<Source>,
    val emojis: Emojis,
)

@JsonClass(generateAdapter = true)
data class Emojis(
    val versionNumber: Int,
    val emojisData: List<EmojiLocalEntity>,
)
