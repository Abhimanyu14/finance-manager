package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    override val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val insertTransactionUseCase: InsertTransactionUseCase,
) : AddTransactionViewModel, ViewModel() {
    override val transactionForValues: Array<TransactionFor> = TransactionFor.values()
    override val transactionTypes: Array<TransactionType> = TransactionType.values()
    override val categories: Flow<List<Category>> = getCategoriesUseCase()
    override var expenseDefaultSource: Source? = null
    override var incomeDefaultSource: Source? = null
    override var expenseDefaultCategory: Category? = null
    override var incomeDefaultCategory: Category? = null
    override var amount: String by mutableStateOf(
        value = "",
    )
    override var category: Category? by mutableStateOf(
        value = null,
    )
    override val categoryTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = category?.title ?: "",
        )
    }
    override var sourceFrom: Source? by mutableStateOf(
        value = null,
    )
    override val sourceFromTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceFrom?.name ?: "",
        )
    }
    override var sourceTo: Source? by mutableStateOf(
        value = null,
    )
    override val sourceToTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceTo?.name ?: "",
        )
    }
    override var description: String by mutableStateOf(
        value = "",
    )
    override var title: String by mutableStateOf(
        value = "",
    )
    override var transactionCalendar: Calendar by mutableStateOf(
        Calendar.getInstance()
    )
    override val transactionDateTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = transactionCalendar.formattedDate(),
        )
    }
    override val transactionTimeTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = transactionCalendar.formattedTime(),
        )
    }
    override var selectedTransactionForIndex: Int by mutableStateOf(
        value = transactionForValues.indexOf(
            element = TransactionFor.SELF,
        ),
    )

    // TODO-Abhi: Private setter
    override var selectedTransactionTypeIndex: Int by mutableStateOf(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val sources: Flow<List<Source>> = flow {
        sourceRepository.sources.collectIndexed { _, value ->
            expenseDefaultSource = value.firstOrNull {
                it.name.contains(
                    other = "Cash",
                    ignoreCase = true,
                )
            }
            incomeDefaultSource = expenseDefaultSource
            sourceFrom = expenseDefaultSource
            emit(
                value = value.sortedWith(
                    comparator = compareBy {
                        it.type.sortOrder
                    }
                ),
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                sourceFrom = null
                sourceTo = incomeDefaultSource
                category = incomeDefaultCategory
            }
            TransactionType.EXPENSE -> {
                sourceFrom = expenseDefaultSource
                sourceTo = null
                category = expenseDefaultCategory
            }
            TransactionType.TRANSFER -> {
                sourceFrom = expenseDefaultSource
                sourceTo = incomeDefaultSource
            }
            TransactionType.ADJUSTMENT -> {}
        }
    }

    override fun insertTransaction() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            insertTransactionUseCase(
                transaction = Transaction(
                    amount = Amount(
                        value = if (transactionTypes[selectedTransactionTypeIndex] == TransactionType.EXPENSE) {
                            -1 * amount.toLong()
                        } else {
                            amount.toLong()
                        },
                    ),
                    categoryId = category?.id ?: 0,
                    sourceFromId = sourceFrom?.id ?: 0,
                    sourceToId = sourceTo?.id ?: 0,
                    description = description,
                    title = if (transactionTypes[selectedTransactionTypeIndex] == TransactionType.TRANSFER) {
                        TransactionType.TRANSFER.title
                    } else {
                        title
                    },
                    creationTimestamp = Calendar.getInstance().timeInMillis,
                    transactionTimestamp = transactionCalendar.timeInMillis,
                    transactionFor = when (transactionTypes[selectedTransactionTypeIndex]) {
                        TransactionType.INCOME -> {
                            TransactionFor.SELF
                        }
                        TransactionType.EXPENSE -> {
                            transactionForValues[selectedTransactionForIndex]
                        }
                        TransactionType.TRANSFER -> {
                            TransactionFor.SELF
                        }
                        TransactionType.ADJUSTMENT -> {
                            TransactionFor.SELF
                        }
                    },
                    transactionType = transactionTypes[selectedTransactionTypeIndex],
                ),
            )
            sourceFrom?.let { sourceFrom ->
                sourceRepository.updateSources(
                    sourceFrom.copy(
                        balanceAmount = sourceFrom.balanceAmount.copy(
                            value = sourceFrom.balanceAmount.value - amount.toLong(),
                        )
                    ),
                )
            }
            sourceTo?.let { sourceTo ->
                sourceRepository.updateSources(
                    sourceTo.copy(
                        balanceAmount = sourceTo.balanceAmount.copy(
                            value = sourceTo.balanceAmount.value + amount.toLong(),
                        )
                    ),
                )
            }
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun getTransactionTypesForNewTransaction(): Array<TransactionType> {
        // TODO-Abhi: Hide transfer when only one source is there
        return TransactionType.values().filter {
            it != TransactionType.ADJUSTMENT
        }.toTypedArray()
    }

    override fun isTitleTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                true
            }
            TransactionType.EXPENSE -> {
                true
            }
            TransactionType.TRANSFER -> {
                false
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }

    override fun isDescriptionTextFieldVisible(): Boolean {
        return false
    }

    override fun isCategoryTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                true
            }
            TransactionType.EXPENSE -> {
                true
            }
            TransactionType.TRANSFER -> {
                false
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }

    override fun isTransactionForRadioGroupVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                false
            }
            TransactionType.EXPENSE -> {
                true
            }
            TransactionType.TRANSFER -> {
                false
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }

    override fun isSourceFromTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                false
            }
            TransactionType.EXPENSE -> {
                true
            }
            TransactionType.TRANSFER -> {
                true
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }

    override fun isSourceToTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                true
            }
            TransactionType.EXPENSE -> {
                false
            }
            TransactionType.TRANSFER -> {
                true
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }

    override fun isValidTransactionData(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                amount.isNotNullOrBlank() && title.isNotNullOrBlank()
            }
            TransactionType.EXPENSE -> {
                amount.isNotNullOrBlank() && title.isNotNullOrBlank()
            }
            TransactionType.TRANSFER -> {
                amount.isNotNullOrBlank() && sourceFrom?.id != sourceTo?.id
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }
}
