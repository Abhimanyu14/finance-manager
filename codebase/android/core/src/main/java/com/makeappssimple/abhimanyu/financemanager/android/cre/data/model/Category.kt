package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category

public fun Category.asEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        parentCategoryId = parentCategoryId,
        subCategoryIds = subCategoryIds,
        description = description,
        emoji = emoji,
        title = title,
        transactionType = transactionType,
    )
}
