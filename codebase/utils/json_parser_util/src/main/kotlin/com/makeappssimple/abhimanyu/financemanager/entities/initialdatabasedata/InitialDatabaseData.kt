package com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata

import com.makeappssimple.abhimanyu.financemanager.entities.Category
import com.makeappssimple.abhimanyu.financemanager.entities.Source
import com.makeappssimple.abhimanyu.financemanager.entities.emojis.Emojis
import com.makeappssimple.abhimanyu.financemanager.entities.emojis.EmojisJsonEntity

interface InitialDatabaseData {
    val defaultCategories: List<Category>
    val defaultSources: List<Source>
    val emojis: Emojis
}
