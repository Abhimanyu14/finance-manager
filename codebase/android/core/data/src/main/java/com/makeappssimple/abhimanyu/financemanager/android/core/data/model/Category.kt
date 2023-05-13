package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

fun Category.asEntity(): CategoryEntity {
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
