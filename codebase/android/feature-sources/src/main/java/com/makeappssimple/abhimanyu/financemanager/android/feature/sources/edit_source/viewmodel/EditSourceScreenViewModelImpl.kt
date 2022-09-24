package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class EditSourceScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourceUseCase: GetSourceUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : EditSourceScreenViewModel, ViewModel() {
    private val _source: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    override val source: StateFlow<Source?> = _source

    override val sourceTypes: List<SourceType> = SourceType.values()
        .filter {
            it != SourceType.CASH
        }

    private val _selectedSourceTypeIndex = MutableStateFlow(
        value = sourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    override val selectedSourceTypeIndex: StateFlow<Int> = _selectedSourceTypeIndex

    private val _name: MutableStateFlow<String> = MutableStateFlow(
        value = source.value?.name.orEmpty(),
    )
    override val name: StateFlow<String> = _name

    private val _balanceAmountValue: MutableStateFlow<String> = MutableStateFlow(
        value = source.value?.balanceAmount?.value.toString(),
    )
    override val balanceAmountValue: StateFlow<String> = _balanceAmountValue

    init {
        savedStateHandle.get<Int>(NavArgs.SOURCE_ID)?.let { sourceId ->
            getSource(
                id = sourceId,
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSource() {
        val source = source.value ?: return
        val amountValue = balanceAmountValue.value.toInt() - source.balanceAmount.value
        val updatedSource = source
            .copy(
                balanceAmount = source.balanceAmount
                    .copy(
                        value = balanceAmountValue.value.toLong(),
                    ),
                type = if (source.type != SourceType.CASH) {
                    sourceTypes[selectedSourceTypeIndex.value]
                } else {
                    source.type
                },
                name = name.value.ifBlank {
                    source.name
                },
            )

        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val sourceFromId = if (amountValue < 0L) {
                updatedSource.id
            } else {
                null
            }
            val sourceToId = if (amountValue < 0L) {
                null
            } else {
                updatedSource.id
            }

            if (amountValue != 0L) {
                insertTransactionsUseCase(
                    Transaction(
                        amount = Amount(
                            value = abs(amountValue),
                        ),
                        categoryId = null,
                        sourceFromId = sourceFromId,
                        sourceToId = sourceToId,
                        description = "",
                        title = TransactionType.ADJUSTMENT.title,
                        creationTimestamp = System.currentTimeMillis(),
                        transactionTimestamp = System.currentTimeMillis(),
                        transactionType = TransactionType.ADJUSTMENT,
                    ),
                )
            }
            updateSourcesUseCase(
                updatedSource,
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun insertSource() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertSourcesUseCase(
                Source(
                    balanceAmount = Amount(
                        value = 0L,
                    ),
                    type = sourceTypes[selectedSourceTypeIndex.value],
                    name = name.value,
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun isValidSourceData(): Boolean {
        return name.value.isNotNullOrBlank()
    }

    override fun clearName() {
        _name.value = ""
    }

    override fun updateName(
        updatedName: String,
    ) {
        _name.value = updatedName
    }

    override fun clearBalanceAmountValue() {
        _balanceAmountValue.value = ""
    }

    override fun updateBalanceAmountValue(
        updatedBalanceAmountValue: String,
    ) {
        _balanceAmountValue.value = updatedBalanceAmountValue
    }

    override fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedSourceTypeIndex.value = updatedIndex
    }

    private fun getSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            _source.update {
                getSourceUseCase(
                    id = id,
                )
            }
            updateInitialSourceValue()
        }
    }

    private fun updateInitialSourceValue() {
        val source = source.value ?: return
        _selectedSourceTypeIndex.update {
            sourceTypes.indexOf(
                element = source.type,
            )
        }
        _name.value = source.name
        _balanceAmountValue.update {
            source.balanceAmount.value.toString()
        }
    }
}
