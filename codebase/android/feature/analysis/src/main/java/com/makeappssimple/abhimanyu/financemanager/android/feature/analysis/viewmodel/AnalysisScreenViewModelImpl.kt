package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataMappedByCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.AnalysisListItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AnalysisScreenViewModelImpl @Inject constructor(
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getTransactionDataMappedByCategoryUseCase: GetTransactionDataMappedByCategoryUseCase,
) : AnalysisScreenViewModel, ViewModel() {
    private val _transactionDataMappedByCategory: MutableStateFlow<List<AnalysisListItemData>> =
        MutableStateFlow(
            value = emptyList(),
        )
    override val transactionDataMappedByCategory: StateFlow<List<AnalysisListItemData>> =
        _transactionDataMappedByCategory

    init {
        getTransactionDataMappedByCategory(
            transactionType = TransactionType.EXPENSE,
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    private fun getTransactionDataMappedByCategory(
        transactionType: TransactionType,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            _transactionDataMappedByCategory.update {
                getTransactionDataMappedByCategoryUseCase(
                    transactionType = transactionType,
                ).map {
                    AnalysisListItemData(
                        amountText = Amount(
                            value = it.amountValue,
                        ).toString(),
                        emoji = it.category.emoji,
                        percentage = "%.2f".format(it.percentage).let { percentage ->
                            "$percentage%"
                        },
                        title = it.category.title,
                    )
                }
            }
        }
    }
}
