package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    val navigationManager: NavigationManager,
) : BaseViewModel() {

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
