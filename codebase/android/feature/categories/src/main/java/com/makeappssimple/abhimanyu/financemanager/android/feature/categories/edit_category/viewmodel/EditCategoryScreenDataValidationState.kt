package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError

public data class EditCategoryScreenDataValidationState(
    val isCtaButtonEnabled: Boolean = false,
    val titleError: EditCategoryScreenTitleError = EditCategoryScreenTitleError.None,
)
