package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.utils.isCashSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

@HiltViewModel
class EditSourceScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val insertSourceUseCase: InsertSourceUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
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
        value = source.value?.name ?: "",
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

    override fun deleteSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteSourceUseCase(
                id = id,
            )
        }
    }

    override fun updateSource() {
        source.value?.let { source ->
            val amountValue = balanceAmountValue.value.toInt() - source.balanceAmount.value
            val updatedSource = source.copy(
                balanceAmount = source.balanceAmount.copy(
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
                if (amountValue != 0L) {
                    insertTransactionUseCase(
                        transaction = Transaction(
                            amount = Amount(
                                value = amountValue,
                            ),
                            categoryId = 0,
                            sourceFromId = 0,
                            sourceToId = updatedSource.id,
                            description = "",
                            title = TransactionType.ADJUSTMENT.title,
                            creationTimestamp = Calendar.getInstance().timeInMillis,
                            transactionTimestamp = Calendar.getInstance().timeInMillis,
                            transactionFor = TransactionFor.SELF,
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
    }

    override fun insertSource() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertSourceUseCase(
                source = Source(
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
        return name.value.isNotNullOrBlank() &&
                !isCashSource(
                    source = name.value,
                )
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
            _source.value = getSourceUseCase(
                id = id,
            )
            updateInitialSourceValue()
        }
    }

    private fun updateInitialSourceValue() {
        source.value?.let {
            _selectedSourceTypeIndex.value = sourceTypes.indexOf(
                element = it.type,
            )
            _name.value = it.name
            _balanceAmountValue.value = it.balanceAmount.value.toString()
        }
    }
}
