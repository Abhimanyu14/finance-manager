package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditTransactionForScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val title: StateFlow<TextFieldValue>
    val transactionFor: StateFlow<TransactionFor?>

    fun insertTransactionFor()

    fun isValidTitle(): Boolean

    fun clearTitle()

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateTransactionFor()
}
