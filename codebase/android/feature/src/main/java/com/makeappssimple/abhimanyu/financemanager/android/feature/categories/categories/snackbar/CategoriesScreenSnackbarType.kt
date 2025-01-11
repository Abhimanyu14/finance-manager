package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenSnackbarType

internal sealed class CategoriesScreenSnackbarType : ScreenSnackbarType {
    data object None : CategoriesScreenSnackbarType()
    data object SetDefaultCategoryFailed : CategoriesScreenSnackbarType()
    data object SetDefaultCategorySuccessful : CategoriesScreenSnackbarType()
}
