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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    sourceRepository: SourceRepository,
    val navigationManager: NavigationManager,
    private val transactionRepository: TransactionRepository,
) : BaseViewModel() {
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
    var source: Source? by mutableStateOf(
        value = null,
    )
    val sourceTextFieldValue by derivedStateOf {
        TextFieldValue(
            text = source?.name ?: "",
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
    var transactionFor by mutableStateOf(
        value = TransactionFor.SELF,
    )
    var transactionType by mutableStateOf(
        value = TransactionType.EXPENSE,
    )
        private set

    var expenseDefaultCategory: Category? = null
    var expenseDefaultSource: Source? = null
    var incomeDefaultCategory: Category? = null
    var incomeDefaultSource: Source? = null


    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun updateTransactionType(
        updatedTransactionType: TransactionType,
    ) {
        transactionType = updatedTransactionType

        when (transactionType) {
            TransactionType.INCOME -> {
                source = incomeDefaultSource
                category = incomeDefaultCategory
            }
            TransactionType.EXPENSE -> {
                source = expenseDefaultSource
                category = expenseDefaultCategory
            }
            TransactionType.TRANSFER -> {}
        }
    }

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
                    sourceFromId = source?.id ?: 0,
                    description = description,
                    title = title,
                    creationTimestamp = Calendar.getInstance().timeInMillis,
                    transactionTimestamp = transactionCalendar.timeInMillis,
                    transactionFor = when (transactionType) {
                        TransactionType.INCOME -> {
                            TransactionFor.SELF
                        }
                        TransactionType.EXPENSE -> {
                            transactionFor
                        }
                        TransactionType.TRANSFER -> {
                            TransactionFor.SELF
                        }
                    },
                    transactionType = transactionType,
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }
}
