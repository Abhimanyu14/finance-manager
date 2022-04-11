package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
) : HomeViewModel, ViewModel() {
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        sourceRepository.sourcesTotalBalanceAmountValue
    override val homeListItemViewData: Flow<List<HomeListItemViewData>> =
        getTransactionsUseCase().map {
            it.map { transaction ->
                HomeListItemViewData(
                    category = transaction.categoryId.let { id ->
                        getCategoryUseCase(
                            id = id,
                        )
                    },
                    transaction = transaction,
                    sourceFrom = transaction.sourceFromId.let { id ->
                        sourceRepository.getSource(
                            id = id,
                        )
                    },
                    sourceTo = transaction.sourceToId?.let { id ->
                        sourceRepository.getSource(
                            id = id,
                        )
                    },
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
            context = Dispatchers.IO,
        ) {
            val transaction = getTransactionUseCase(
                id = id,
            )
            deleteTransactionUseCase(
                id = id,
            )
            val sourceFromId = transaction?.sourceFromId
            val sourceToId = transaction?.sourceToId
            if (
                sourceFromId != null &&
                sourceToId != null &&
                sourceFromId != 0 &&
                sourceToId != 0
            ) {
                sourceRepository.getSource(
                    id = sourceFromId,
                )?.let { sourceFrom ->
                    sourceRepository.updateSources(
                        sourceFrom.copy(
                            balanceAmount = sourceFrom.balanceAmount.copy(
                                value = sourceFrom.balanceAmount.value + transaction.amount.value,
                            )
                        ),
                    )
                }
                sourceRepository.getSource(
                    id = sourceToId,
                )?.let { sourceTo ->
                    sourceRepository.updateSources(
                        sourceTo.copy(
                            balanceAmount = sourceTo.balanceAmount.copy(
                                value = sourceTo.balanceAmount.value - transaction.amount.value,
                            )
                        ),
                    )
                }
            } else {
                sourceFromId?.let {
                    sourceRepository.getSource(
                        id = transaction.sourceFromId,
                    )?.let { sourceFrom ->
                        sourceRepository.updateSources(
                            sourceFrom.copy(
                                balanceAmount = sourceFrom.balanceAmount.copy(
                                    value = sourceFrom.balanceAmount.value - transaction.amount.value,
                                )
                            ),
                        )
                    }
                }
                sourceToId?.let {
                    sourceRepository.getSource(
                        id = transaction.sourceToId,
                    )?.let { sourceTo ->
                        sourceRepository.updateSources(
                            sourceTo.copy(
                                balanceAmount = sourceTo.balanceAmount.copy(
                                    value = sourceTo.balanceAmount.value - transaction.amount.value,
                                )
                            ),
                        )
                    }
                }
            }
        }
    }
}
