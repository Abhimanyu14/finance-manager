package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenTitleError

public data class AddCategoryScreenDataValidationState(
    val isCtaButtonEnabled: Boolean = false,
    val titleError: AddCategoryScreenTitleError = AddCategoryScreenTitleError.None,
)
