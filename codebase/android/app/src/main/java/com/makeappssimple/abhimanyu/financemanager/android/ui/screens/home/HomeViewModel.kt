package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val navigationManager: NavigationManager,
) : BaseViewModel() {

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
