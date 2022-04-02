package com.makeappssimple.abhimanyu.financemanager.android.entities.initialdatabasedata

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitialDatabaseData(
    val sources: List<Source>,
    val categories: List<Category>,
    val emojis: List<EmojiLocalEntity>,
)
