package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.minus
import com.makeappssimple.abhimanyu.financemanager.android.core.model.plus
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // region refund transaction data
    private var originalTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null
    // endregion

    // region default data
    private var defaultDataIdFromDataStore: DefaultDataId? = null
    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null
    // endregion

    // region initial data
    private var accounts: MutableList<Account> = mutableListOf()
    private var categories: MutableList<Category> = mutableListOf()
    private var transactionForValues: MutableList<TransactionFor> = mutableListOf()
    private var validTransactionTypesForNewTransaction: MutableList<TransactionType> =
        mutableListOf()
    // endregion

    // region add transaction screen initial data
    private val _addTransactionScreenInitialData: MutableStateFlow<AddTransactionScreenInitialData?> =
        MutableStateFlow(
            value = null,
        )
    public val addTransactionScreenInitialData: StateFlow<AddTransactionScreenInitialData?> =
        _addTransactionScreenInitialData
    // endregion

    // region title suggestions
    private val title: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    public val titleSuggestions: StateFlow<ImmutableList<String>> = combine(
        flow = title,
        flow2 = category
    ) { title, category ->
        category?.id?.let { categoryId ->
            getTitleSuggestionsUseCase(
                categoryId = categoryId,
                enteredTitle = title,
            )
        } ?: persistentListOf()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    // endregion

    public fun initViewModel() {
        viewModelScope.launch {
            fetchData()
            processData()
        }
    }

    public fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ) {
        viewModelScope.launch {
            val id = insertTransactionUseCase(
                amountValue = amountValue,
                accountFrom = accountFrom,
                accountTo = accountTo,
                transaction = transaction,
            )

            // Only for refund transaction
            originalTransactionData?.transaction?.let { originalTransaction ->
                val refundTransactionIds = originalTransaction.refundTransactionIds?.run {
                    originalTransaction.refundTransactionIds?.toMutableList()
                } ?: mutableListOf()
                refundTransactionIds.add(id.toInt())
                updateTransactionUseCase(
                    originalTransaction = originalTransaction,
                    updatedTransaction = originalTransaction.copy(
                        refundTransactionIds = refundTransactionIds,
                    ),
                )
            }
            navigator.navigateUp()
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun setTitle(
        updatedTitle: String,
    ) {
        title.update {
            updatedTitle
        }
    }

    public fun setCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    private suspend fun fetchData() {
        coroutineScope {
            joinAll(
                launch {
                    defaultDataIdFromDataStore = myPreferencesRepository.getDefaultDataId()
                },
                launch {
                    categories.addAll(getAllCategoriesUseCase())
                },
                launch {
                    accounts.addAll(
                        getAllAccountsUseCase()
                            .sortedWith(
                                comparator = compareBy<Account> {
                                    it.type.sortOrder
                                }.thenByDescending {
                                    it.balanceAmount.value
                                }
                            )
                    )
                },
                launch {
                    transactionForValues.addAll(getAllTransactionForValuesUseCase())
                },
            )
        }
    }

    private suspend fun processData() {
        setDefaultCategory()
        setDefaultAccount()
        setValidTransactionTypesForNewTransaction()
        getOriginalTransactionData()
        calculateMaxRefundAmount()
        setAddTransactionScreenInitialData()
    }

    private fun setDefaultCategory() {
        defaultExpenseCategory = defaultDataIdFromDataStore?.expenseCategory?.let { categoryId ->
            getCategory(
                categoryId = categoryId,
            )
        } ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = defaultDataIdFromDataStore?.incomeCategory?.let { categoryId ->
            getCategory(
                categoryId = categoryId,
            )
        } ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory =
            defaultDataIdFromDataStore?.investmentCategory?.let { categoryId ->
                getCategory(
                    categoryId = categoryId,
                )
            } ?: categories.firstOrNull { category ->
                isDefaultInvestmentCategory(
                    category = category.title,
                )
            }
    }

    private fun setDefaultAccount() {
        defaultAccount = defaultDataIdFromDataStore?.account?.let { accountId ->
            getAccount(
                accountId = accountId,
            )
        } ?: accounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun setValidTransactionTypesForNewTransaction() {
        val validTransactionTypes = mutableListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
        if (accounts.size > 1) {
            validTransactionTypes.add(TransactionType.TRANSFER)
        }
        validTransactionTypesForNewTransaction.addAll(validTransactionTypes)
    }

    private suspend fun getOriginalTransactionData() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        originalTransactionData = getTransactionDataUseCase(
            id = originalTransactionId,
        )
    }

    private suspend fun calculateMaxRefundAmount() {
        val originalTransactionData: TransactionData = originalTransactionData ?: return
        var refundedAmountCalculated: Amount? = null
        originalTransactionData.transaction.refundTransactionIds?.forEach { refundTransactionId ->
            if (refundTransactionId != getOriginalTransactionId()) {
                getTransactionDataUseCase(
                    id = refundTransactionId,
                )?.transaction?.amount?.let { prevRefundedTransactionAmount ->
                    refundedAmountCalculated = refundedAmountCalculated?.run {
                        this.plus(prevRefundedTransactionAmount)
                    } ?: prevRefundedTransactionAmount
                }
            }
        }
        originalTransactionData.transaction.amount.let { originalTransactionAmount ->
            maxRefundAmount = if (refundedAmountCalculated.isNotNull()) {
                originalTransactionAmount.minus((refundedAmountCalculated ?: Amount()))
            } else {
                originalTransactionAmount
            }
        }
    }

    private fun setAddTransactionScreenInitialData() {
        val defaultAccount = defaultAccount
        val defaultExpenseCategory = defaultExpenseCategory
        val defaultIncomeCategory = defaultIncomeCategory
        val defaultInvestmentCategory = defaultInvestmentCategory
        val accounts = accounts.toImmutableList()
        val categories = categories.toImmutableList()
        val transactionForValues = transactionForValues.toImmutableList()
        val validTransactionTypesForNewTransaction =
            validTransactionTypesForNewTransaction.toImmutableList()

        if (defaultAccount != null &&
            defaultExpenseCategory != null &&
            defaultIncomeCategory != null &&
            defaultInvestmentCategory != null &&
            accounts.isNotEmpty() &&
            categories.isNotEmpty() &&
            transactionForValues.isNotEmpty() &&
            validTransactionTypesForNewTransaction.isNotEmpty()
        ) {
            _addTransactionScreenInitialData.update {
                AddTransactionScreenInitialData(
                    defaultAccount = defaultAccount,
                    defaultExpenseCategory = defaultExpenseCategory,
                    defaultIncomeCategory = defaultIncomeCategory,
                    defaultInvestmentCategory = defaultInvestmentCategory,
                    accounts = accounts,
                    categories = categories,
                    transactionForValues = transactionForValues,
                    validTransactionTypesForNewTransaction = validTransactionTypesForNewTransaction,
                    originalTransactionData = originalTransactionData,
                    maxRefundAmount = maxRefundAmount,
                )
            }
        }
    }

    private fun getCategory(
        categoryId: Int,
    ): Category? {
        return categories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int,
    ): Account? {
        return accounts.find { account ->
            account.id == accountId
        }
    }

    private fun getOriginalTransactionId(): Int? {
        return screenArgs.originalTransactionId
    }
}
