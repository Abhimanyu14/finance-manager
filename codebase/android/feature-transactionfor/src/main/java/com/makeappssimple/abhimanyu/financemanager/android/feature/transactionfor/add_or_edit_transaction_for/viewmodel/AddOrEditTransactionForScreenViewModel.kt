package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditTransactionForScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val name: StateFlow<String>

    fun insertTransactionFor()

    fun isValidName(): Boolean

    fun clearName()

    fun updateName(
        updatedName: String,
    )

    fun updateTransactionFor()
}
