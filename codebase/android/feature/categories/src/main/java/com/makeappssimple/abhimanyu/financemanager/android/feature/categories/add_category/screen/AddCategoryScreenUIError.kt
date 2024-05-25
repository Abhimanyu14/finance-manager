package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.annotation.StringRes
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

public enum class AddCategoryScreenUIError(
    @StringRes public val textStringResourceId: Int,
) {
    CATEGORY_EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_category_error_category_exists,
    ),
}
