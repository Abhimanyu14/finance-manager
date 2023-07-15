package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditTransactionForScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<AddOrEditTransactionForScreenUIData>?>

    fun insertTransactionFor()

    fun clearTitle()

    fun navigateUp()

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateTransactionFor()
}
