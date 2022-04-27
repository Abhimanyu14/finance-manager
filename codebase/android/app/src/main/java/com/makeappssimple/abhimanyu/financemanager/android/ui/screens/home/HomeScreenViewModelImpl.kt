package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetRecentTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModelImpl @Inject constructor(
    getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSourceUseCase: GetSourceUseCase,
) : HomeScreenViewModel, ViewModel() {
    override val homeListItemViewData: Flow<List<HomeListItemViewData>> = getRecentTransactionsUseCase()
        .map { transactions ->
            transactions
                .map { transaction ->
                    val category = if (transaction.categoryId != null) {
                        getCategoryUseCase(
                            id = transaction.categoryId,
                        )
                    } else {
                        null
                    }
                    val sourceFrom = if (transaction.sourceFromId != null) {
                        getSourceUseCase(
                            id = transaction.sourceFromId,
                        )
                    } else {
                        null
                    }
                    val sourceTo = if (transaction.sourceToId != null) {
                        getSourceUseCase(
                            id = transaction.sourceToId,
                        )
                    } else {
                        null
                    }
                    HomeListItemViewData(
                        category = category,
                        transaction = transaction,
                        sourceFrom = sourceFrom,
                        sourceTo = sourceTo,
                    )
                }
        }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionAndRevertOtherDataUseCase(
                id = id,
            )
        }
    }
}
