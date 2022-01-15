package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val navigationManager: NavigationManager,
) : BaseViewModel() {

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
