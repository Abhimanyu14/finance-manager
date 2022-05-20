package com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata

import com.makeappssimple.abhimanyu.financemanager.entities.Category
import com.makeappssimple.abhimanyu.financemanager.entities.Source
import com.makeappssimple.abhimanyu.financemanager.entities.emojis.EmojisJsonEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitialDatabaseInputDataEntity(
    override val defaultCategories: List<Category>,
    override val defaultSources: List<Source>,
    override val emojis: EmojisJsonEntity,
): InitialDatabaseData
