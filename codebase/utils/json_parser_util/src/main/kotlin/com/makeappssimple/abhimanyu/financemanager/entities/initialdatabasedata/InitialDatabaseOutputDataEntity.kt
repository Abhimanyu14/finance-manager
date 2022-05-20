package com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata

import com.makeappssimple.abhimanyu.financemanager.entities.Category
import com.makeappssimple.abhimanyu.financemanager.entities.Source
import com.makeappssimple.abhimanyu.financemanager.entities.emojis.EmojisLocalEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitialDatabaseOutputDataEntity(
    override val defaultCategories: List<Category>,
    override val defaultSources: List<Source>,
    override val emojis: EmojisLocalEntity,
) : InitialDatabaseData {
    constructor(
        data: InitialDatabaseData,
    ) : this(
        defaultCategories = data.defaultCategories,
        defaultSources = data.defaultSources,
        emojis = EmojisLocalEntity(data.emojis),
    )
}
