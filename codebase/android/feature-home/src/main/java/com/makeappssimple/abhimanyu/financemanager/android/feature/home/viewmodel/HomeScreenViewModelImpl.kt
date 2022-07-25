package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeListItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSourceUseCase: GetSourceUseCase,
) : HomeScreenViewModel, ViewModel() {
    override val homeListItemViewData: Flow<List<HomeListItemViewData>> =
        getRecentTransactionsUseCase()
            .map { transactions ->
                transactions
                    .map { transaction ->
                        val category = transaction.categoryId?.let { categoryId ->
                            getCategoryUseCase(
                                id = categoryId,
                            )
                        }
                        val sourceFrom = transaction.sourceFromId?.let { sourceFromId ->
                            getSourceUseCase(
                                id = sourceFromId,
                            )
                        }
                        val sourceTo = transaction.sourceToId?.let { sourceToId ->
                            getSourceUseCase(
                                id = sourceToId,
                            )
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
