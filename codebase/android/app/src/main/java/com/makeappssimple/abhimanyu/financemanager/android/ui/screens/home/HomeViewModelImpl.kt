package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
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
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val getSourceUseCase: GetSourceUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : HomeViewModel, ViewModel() {
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()
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
                        getSourceUseCase(
                            id = id,
                        )
                    },
                    sourceTo = transaction.sourceToId?.let { id ->
                        getSourceUseCase(
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
                getSourceUseCase(
                    id = sourceFromId,
                )?.let { sourceFrom ->
                    updateSourcesUseCase(
                        sourceFrom.copy(
                            balanceAmount = sourceFrom.balanceAmount.copy(
                                value = sourceFrom.balanceAmount.value + transaction.amount.value,
                            )
                        ),
                    )
                }
                getSourceUseCase(
                    id = sourceToId,
                )?.let { sourceTo ->
                    updateSourcesUseCase(
                        sourceTo.copy(
                            balanceAmount = sourceTo.balanceAmount.copy(
                                value = sourceTo.balanceAmount.value - transaction.amount.value,
                            )
                        ),
                    )
                }
            } else {
                sourceFromId?.let {
                    getSourceUseCase(
                        id = transaction.sourceFromId,
                    )?.let { sourceFrom ->
                        updateSourcesUseCase(
                            sourceFrom.copy(
                                balanceAmount = sourceFrom.balanceAmount.copy(
                                    value = sourceFrom.balanceAmount.value - transaction.amount.value,
                                )
                            ),
                        )
                    }
                }
                sourceToId?.let {
                    getSourceUseCase(
                        id = transaction.sourceToId,
                    )?.let { sourceTo ->
                        updateSourcesUseCase(
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
