package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components.TransactionsListItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionsUseCase: GetAllTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSourceUseCase: GetSourceUseCase,
) : TransactionsScreenViewModel, ViewModel() {
    override val categories: Flow<List<Category>> = getCategoriesUseCase()
    override val sources: Flow<List<Source>> = getSourcesUseCase()
    override val transactionsListItemViewData: Flow<List<TransactionsListItemViewData>> =
        getAllTransactionsUseCase()
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
                        TransactionsListItemViewData(
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
