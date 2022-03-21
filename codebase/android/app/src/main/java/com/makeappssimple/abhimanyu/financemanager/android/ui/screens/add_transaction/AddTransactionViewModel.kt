package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.local.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.local.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val transactionRepository: TransactionRepository,
) : BaseViewModel() {
    val transactionForValues = TransactionFor.values()
    val transactionTypes = TransactionType.values()
    val categories: Flow<List<Category>> = categoryRepository.categories
    val sources: Flow<List<Source>> = sourceRepository.sources

    var amount by mutableStateOf(
        value = "",
    )
    var category: Category? by mutableStateOf(
        value = null,
    )
    val categoryTextFieldValue by derivedStateOf {
        TextFieldValue(
            text = category?.title ?: "",
        )
    }
    var sourceFrom: Source? by mutableStateOf(
        value = null,
    )
    val sourceFromTextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceFrom?.name ?: "",
        )
    }
    var sourceTo: Source? by mutableStateOf(
        value = null,
    )
    val sourceToTextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceTo?.name ?: "",
        )
    }
    var description by mutableStateOf(
        value = "",
    )
    var title by mutableStateOf(
        value = "",
    )
    var transactionCalendar: Calendar by mutableStateOf(
        Calendar.getInstance()
    )
    val transactionDateTextFieldValue by derivedStateOf {
        TextFieldValue(
            text = transactionCalendar.formattedDate(),
        )
    }
    var selectedTransactionForIndex by mutableStateOf(
        value = transactionForValues.indexOf(
            element = TransactionFor.SELF,
        ),
    )
    var selectedTransactionTypeIndex by mutableStateOf(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
        private set

    var expenseDefaultCategory: Category? = null
    var expenseDefaultSource: Source? = null
    var incomeDefaultCategory: Category? = null
    var incomeDefaultSource: Source? = null


    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun updateSelectedTransactionTypeIndex(
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
        }
    }

    // TODO-Abhi: Restrict transfer between same source
    fun insertTransaction() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            transactionRepository.insertTransaction(
                transaction = Transaction(
                    amount = Amount(
                        value = amount.toLong(),
                    ),
                    categoryId = category?.id ?: 0,
                    sourceFromId = sourceFrom?.id ?: 0,
                    sourceToId = sourceTo?.id ?: 0,
                    description = description,
                    title = title,
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
}
