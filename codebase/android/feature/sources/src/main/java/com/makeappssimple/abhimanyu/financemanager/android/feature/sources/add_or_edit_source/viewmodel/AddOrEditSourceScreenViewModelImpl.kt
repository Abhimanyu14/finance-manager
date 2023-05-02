package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getCurrentTimeMillis
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditSourceScreenViewModelImpl @Inject constructor(
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    savedStateHandle: SavedStateHandle,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourceUseCase: GetSourceUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : AddOrEditSourceScreenViewModel, ViewModel() {
    // Navigation parameters
    private var originalSourceId: Int? = savedStateHandle.get<Int>(NavArgs.SOURCE_ID)

    private val sources: StateFlow<List<Source>> = getAllSourcesFlowUseCase().defaultListStateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
    )
    private val _source: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    override val source: StateFlow<Source?> = _source

    override val sourceTypes: List<SourceType> = SourceType.values()
        .filter {
            it != SourceType.CASH
        }

    private val _selectedSourceTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = sourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    override val selectedSourceTypeIndex: StateFlow<Int> = _selectedSourceTypeIndex

    private val _name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    override val name: StateFlow<TextFieldValue> = _name

    private val _balanceAmountValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    override val balanceAmountValue: StateFlow<TextFieldValue> = _balanceAmountValue

    init {
        originalSourceId?.let {
            getSource(
                id = it,
            )
        }
    }

    override fun updateSource() {
        val source = source.value ?: return
        val amountValue = balanceAmountValue.value.text.toInt() - source.balanceAmount.value
        val updatedSource = source
            .copy(
                balanceAmount = source.balanceAmount
                    .copy(
                        value = balanceAmountValue.value.text.toLong(),
                    ),
                type = if (source.type != SourceType.CASH) {
                    sourceTypes[selectedSourceTypeIndex.value]
                } else {
                    source.type
                },
                name = name.value.text.ifBlank {
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
                        creationTimestamp = getCurrentTimeMillis(),
                        transactionTimestamp = getCurrentTimeMillis(),
                        transactionType = TransactionType.ADJUSTMENT,
                    ),
                )
            }
            updateSourcesUseCase(
                updatedSource,
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
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
                    name = name.value.text,
                ),
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun isValidSourceData(): Boolean {
        val name = name.value.text

        // TODO-Abhi: Error message - "Name can not be empty"
        if (name.isBlank()) {
            return false
        }

        // TODO-Abhi: Error message - "Name already exists"
        if (isDefaultSource(
                source = name,
            )
        ) {
            return false
        }

        // TODO-Abhi: Error message - "Name already exists"
        if (source.value?.name != name && sources.value.find {
                it.name.equalsIgnoringCase(
                    other = name,
                )
            }.isNotNull()
        ) {
            return false
        }

        return true
    }

    override fun clearName() {
        updateName(
            updatedName = name.value.copy(""),
        )
    }

    override fun updateName(
        updatedName: TextFieldValue,
    ) {
        _name.update {
            updatedName
        }
    }

    override fun clearBalanceAmountValue() {
        updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    override fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    ) {
        _balanceAmountValue.update {
            updatedBalanceAmountValue.copy(
                text = updatedBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    override fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedSourceTypeIndex.update {
            updatedIndex
        }
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
        updateSelectedSourceTypeIndex(
            updatedIndex = sourceTypes.indexOf(
                element = source.type,
            ),
        )
        updateName(
            updatedName = name.value.copy(
                text = source.name,
            )
        )
        _balanceAmountValue.update {
            TextFieldValue(
                text = source.balanceAmount.value.toString(),
                selection = TextRange(source.balanceAmount.value.toString().length),
            )
        }
    }
}
