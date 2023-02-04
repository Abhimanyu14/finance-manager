package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditTransactionForScreenViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
) : AddOrEditTransactionForScreenViewModel, ViewModel() {
    private val _name: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val name: StateFlow<String> = _name

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateTransactionFor() {
//        val source = source.value ?: return
//        val amountValue = balanceAmountValue.value.toInt() - source.balanceAmount.value
//        val updatedSource = source
//            .copy(
//                balanceAmount = source.balanceAmount
//                    .copy(
//                        value = balanceAmountValue.value.toLong(),
//                    ),
//                type = if (source.type != SourceType.CASH) {
//                    sourceTypes[selectedSourceTypeIndex.value]
//                } else {
//                    source.type
//                },
//                name = name.value.ifBlank {
//                    source.name
//                },
//            )

        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
//            val sourceFromId = if (amountValue < 0L) {
//                updatedSource.id
//            } else {
//                null
//            }
//            val sourceToId = if (amountValue < 0L) {
//                null
//            } else {
//                updatedSource.id
//            }
//
//            if (amountValue != 0L) {
//                insertTransactionsUseCase(
//                    Transaction(
//                        amount = Amount(
//                            value = abs(amountValue),
//                        ),
//                        categoryId = null,
//                        sourceFromId = sourceFromId,
//                        sourceToId = sourceToId,
//                        description = "",
//                        title = TransactionType.ADJUSTMENT.title,
//                        creationTimestamp = System.currentTimeMillis(),
//                        transactionTimestamp = System.currentTimeMillis(),
//                        transactionType = TransactionType.ADJUSTMENT,
//                    ),
//                )
//            }
//            updateSourcesUseCase(
//                updatedSource,
//            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun insertTransactionFor() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertTransactionForUseCase(
                TransactionFor(
                    title = name.value,
                )
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun isValidName(): Boolean {
        // TODO-Abhi: Add check to avoid duplicates
        return name.value.isNotNullOrBlank()
    }

    override fun clearName() {
        updateName(
            updatedName = "",
        )
    }

    override fun updateName(
        updatedName: String,
    ) {
        _name.update {
            updatedName
        }
    }
}
