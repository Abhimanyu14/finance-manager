package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.navigation.AddOrEditSourceScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditSourceScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : AddOrEditSourceScreenViewModel, ViewModel() {
    private val addOrEditSourceScreenArgs: AddOrEditSourceScreenArgs = AddOrEditSourceScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private lateinit var sources: List<Source>
    private val originalSource: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    private val validSourceTypes: List<SourceType> = SourceType.values().filter {
        it != SourceType.CASH
    }
    private val errorData: MutableStateFlow<AddOrEditSourceScreenUIErrorData> = MutableStateFlow(
        value = AddOrEditSourceScreenUIErrorData(),
    )
    private val selectedSourceTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validSourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    private val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    private val balanceAmountValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )

    override val screenUIData: StateFlow<AddOrEditSourceScreenUIData?> = combine(
        errorData,
        selectedSourceTypeIndex,
        balanceAmountValue,
        name,
        originalSource,
    ) { flows ->
        val errorData = flows[0] as? AddOrEditSourceScreenUIErrorData
            ?: AddOrEditSourceScreenUIErrorData()
        val selectedSourceTypeIndex = flows[1] as? Int ?: 0
        val balanceAmountValue = flows[2] as? TextFieldValue ?: TextFieldValue()
        val name = flows[3] as? TextFieldValue ?: TextFieldValue()
        val originalSource = flows[4] as? Source

        val sourceIsNotCash = originalSource?.type != SourceType.CASH
        val isValidSourceData = checkIfSourceDataIsValid(
            name = name.text,
            originalSource = originalSource,
        )
        AddOrEditSourceScreenUIData(
            visibilityData = AddOrEditSourceScreenUIVisibilityData(
                balanceAmount = false,
                name = sourceIsNotCash,
                sourceTypes = sourceIsNotCash,
            ),
            errorData = errorData,
            isValidSourceData = isValidSourceData,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = validSourceTypes,
            balanceAmountValue = balanceAmountValue,
            name = name,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            getAllSources()
            getOriginalSource()
        }
    }

    override fun updateSource() {
        val originalSourceValue = originalSource.value ?: return
        val amountValue = balanceAmountValue.value.text.toInt() - originalSourceValue.balanceAmount.value
        val updatedSource = originalSourceValue
            .copy(
                balanceAmount = originalSourceValue.balanceAmount
                    .copy(
                        value = balanceAmountValue.value.text.toLong(),
                    ),
                type = if (originalSourceValue.type != SourceType.CASH) {
                    validSourceTypes[selectedSourceTypeIndex.value]
                } else {
                    originalSourceValue.type
                },
                name = name.value.text.ifBlank {
                    originalSourceValue.name
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
                        creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                        transactionTimestamp = dateTimeUtil.getCurrentTimeMillis(),
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
                    type = validSourceTypes[selectedSourceTypeIndex.value],
                    name = name.value.text,
                ),
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun clearName() {
        updateName(
            updatedName = name.value.copy(""),
        )
    }

    override fun updateName(
        updatedName: TextFieldValue,
    ) {
        name.update {
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
        balanceAmountValue.update {
            updatedBalanceAmountValue.copy(
                text = updatedBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    override fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    ) {
        selectedSourceTypeIndex.update {
            updatedIndex
        }
    }

    private fun getAllSources() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            sources = getAllSourcesUseCase()
        }
    }

    private fun getOriginalSource() {
        addOrEditSourceScreenArgs.originalSourceId?.let { id ->
            viewModelScope.launch(
                context = dispatcherProvider.io,
            ) {
                getSourceUseCase(
                    id = id,
                )?.let { fetchedOriginalSource ->
                    originalSource.update {
                        fetchedOriginalSource
                    }
                    updateInitialSourceValue(
                        source = fetchedOriginalSource,
                    )
                }
            }
        }
    }

    private fun updateInitialSourceValue(
        source: Source,
    ) {
        updateSelectedSourceTypeIndex(
            updatedIndex = validSourceTypes.indexOf(
                element = source.type,
            ),
        )
        updateName(
            updatedName = name.value.copy(
                text = source.name,
            )
        )
        balanceAmountValue.update {
            TextFieldValue(
                text = source.balanceAmount.value.toString(),
                selection = TextRange(source.balanceAmount.value.toString().length),
            )
        }
    }

    private fun checkIfSourceDataIsValid(
        name: String,
        originalSource: Source?,
    ): Boolean {
        // TODO-Abhi: Error message - "Name can not be empty"
        if (name.isBlank()) {
            return false
        }

        if (
            isDefaultSource(
                source = name.trim(),
            )
        ) {
            errorData.update {
                errorData.value.copy(
                    name = "Source already exists" // TODO(Abhi): Move to string resources
                )
            }
            return false
        }

        val doesNotExist = if (::sources.isInitialized) {
            sources.find {
                it.name.trim().equalsIgnoringCase(
                    other = name.trim(),
                )
            }.isNull()
        } else {
            true
        }

        val result = name.trim() == originalSource?.name?.trim() || doesNotExist
        errorData.update {
            if (result) {
                AddOrEditSourceScreenUIErrorData()
            } else {
                errorData.value.copy(
                    name = "Source already exists" // TODO(Abhi): Move to string resources
                )
            }
        }

        return result
    }
}
