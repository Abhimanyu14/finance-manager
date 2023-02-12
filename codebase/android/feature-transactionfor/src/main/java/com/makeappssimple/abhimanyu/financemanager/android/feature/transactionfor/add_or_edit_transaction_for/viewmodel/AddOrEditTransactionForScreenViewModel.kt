package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditTransactionForScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val title: StateFlow<String>
    val transactionFor: StateFlow<TransactionFor?>

    fun insertTransactionFor()

    fun isValidTitle(): Boolean

    fun clearTitle()

    fun updateTitle(
        updatedTitle: String,
    )

    fun updateTransactionFor()
}
