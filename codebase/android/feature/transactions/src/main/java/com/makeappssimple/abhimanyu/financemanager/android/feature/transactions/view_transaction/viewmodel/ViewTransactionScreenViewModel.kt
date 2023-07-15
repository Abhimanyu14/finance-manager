package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<ViewTransactionScreenUIData>?>

    fun getTransactionData()

    fun deleteTransaction(
        id: Int,
    )

    fun navigateToAddTransactionScreen(
        transactionId: Int,
    )

    fun navigateToEditTransactionScreen(
        transactionId: Int,
    )

    fun navigateUp()
}
