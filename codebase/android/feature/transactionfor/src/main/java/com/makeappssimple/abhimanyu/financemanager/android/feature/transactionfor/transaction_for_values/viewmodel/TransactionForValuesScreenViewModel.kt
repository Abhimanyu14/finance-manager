package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface TransactionForValuesScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<TransactionForValuesScreenUIData>?>

    fun deleteTransactionFor(
        id: Int,
    )

    fun navigateToAddTransactionForScreen()

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    )

    fun navigateUp()
}
