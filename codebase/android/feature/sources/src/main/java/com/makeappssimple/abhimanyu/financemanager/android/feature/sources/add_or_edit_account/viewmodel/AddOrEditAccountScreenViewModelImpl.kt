package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.screen.AddOrEditAccountScreenUIErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.screen.AddOrEditAccountScreenUIErrorText
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.navigation.AddOrEditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
internal class AddOrEditAccountScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val myLogger: MyLogger,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigationManager: NavigationManager,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) : AddOrEditAccountScreenViewModel, ViewModel() {
    private val addOrEditAccountScreenArgs: AddOrEditAccountScreenArgs = AddOrEditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private lateinit var accounts: List<Source>
    private val originalAccount: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    private val validAccountTypes: List<SourceType> = SourceType.values().filter {
        it != SourceType.CASH
    }
    private val errorData: MutableStateFlow<AddOrEditAccountScreenUIErrorData> = MutableStateFlow(
        value = AddOrEditAccountScreenUIErrorData(),
    )
    private val selectedAccountTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validAccountTypes.indexOf(
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

    override val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?> = combine(
        errorData,
        selectedAccountTypeIndex,
        balanceAmountValue,
        name,
        originalAccount,
    ) { flows ->
        val errorData = flows[0] as? AddOrEditAccountScreenUIErrorData
            ?: AddOrEditAccountScreenUIErrorData()
        val selectedAccountTypeIndex = flows[1] as? Int ?: 0
        val balanceAmountValue = flows[2] as? TextFieldValue ?: TextFieldValue()
        val name = flows[3] as? TextFieldValue ?: TextFieldValue()
        val originalAccount = flows[4] as? Source

        val accountIsNotCash = originalAccount?.type != SourceType.CASH
        val isValidAccountData = checkIfAccountDataIsValid(
            name = name.text,
            originalAccount = originalAccount,
        )

        if (
            errorData.isNull() ||
            isValidAccountData.isNull() ||
            accountIsNotCash.isNull() ||
            selectedAccountTypeIndex.isNull() ||
            balanceAmountValue.isNull() ||
            name.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddOrEditAccountScreenUIData(
                    errorData = errorData,
                    isValidAccountData = isValidAccountData,
                    accountIsNotCash = accountIsNotCash,
                    selectedAccountTypeIndex = selectedAccountTypeIndex,
                    accountTypes = validAccountTypes,
                    balanceAmountValue = balanceAmountValue,
                    name = name,
                ),
            )
        }
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
            getAllAccounts()
            getOriginalAccount()
        }
    }

    override fun updateAccount() {
        val originalAccountValue = originalAccount.value ?: return
        val amountChangeValue =
            balanceAmountValue.value.text.toIntOrZero() - originalAccountValue.balanceAmount.value
        val updatedAccount = originalAccountValue
            .copy(
                balanceAmount = originalAccountValue.balanceAmount
                    .copy(
                        value = balanceAmountValue.value.text.toLongOrZero(),
                    ),
                type = if (originalAccountValue.type != SourceType.CASH) {
                    validAccountTypes[selectedAccountTypeIndex.value]
                } else {
                    originalAccountValue.type
                },
                name = name.value.text.ifBlank {
                    originalAccountValue.name
                },
            )

        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val sourceFromId = if (amountChangeValue < 0L) {
                updatedAccount.id
            } else {
                null
            }
            val sourceToId = if (amountChangeValue < 0L) {
                null
            } else {
                updatedAccount.id
            }

            if (amountChangeValue != 0L) {
                insertTransactionsUseCase(
                    Transaction(
                        amount = Amount(
                            value = abs(amountChangeValue),
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
            updateAccountsUseCase(
                updatedAccount,
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun insertAccount() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertAccountsUseCase(
                Source(
                    balanceAmount = Amount(
                        value = 0L,
                    ),
                    type = validAccountTypes[selectedAccountTypeIndex.value],
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

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
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

    override fun updateSelectedAccountTypeIndex(
        updatedIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedIndex
        }
    }

    private fun getAllAccounts() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            accounts = getAllAccountsUseCase()
        }
    }

    private fun getOriginalAccount() {
        addOrEditAccountScreenArgs.originalAccountId?.let { id ->
            viewModelScope.launch(
                context = dispatcherProvider.io,
            ) {
                getAccountUseCase(
                    id = id,
                )?.let { fetchedOriginalAccount ->
                    originalAccount.update {
                        fetchedOriginalAccount
                    }
                    updateInitialAccountValue(
                        source = fetchedOriginalAccount,
                    )
                }
            }
        }
    }

    private fun updateInitialAccountValue(
        source: Source,
    ) {
        updateSelectedAccountTypeIndex(
            updatedIndex = validAccountTypes.indexOf(
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

    private fun checkIfAccountDataIsValid(
        name: String,
        originalAccount: Source?,
    ): Boolean {
        if (name.isBlank()) {
            return false
        }

        if (
            (originalAccount.isNull() && isDefaultAccount(
                source = name.trim(),
            )) || (originalAccount.isNotNull() && isDefaultAccount(
                source = name.trim(),
            ) && isDefaultAccount(
                source = originalAccount.name,
            ).not())
        ) {
            errorData.update {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIErrorText.ACCOUNT_EXISTS
                )
            }
            return false
        }

        val doesNotExist = if (::accounts.isInitialized) {
            accounts.find {
                it.name.trim().equalsIgnoringCase(
                    other = name.trim(),
                )
            }.isNull()
        } else {
            true
        }

        val isValidData = name.trim() == originalAccount?.name?.trim() || doesNotExist
        errorData.update {
            if (isValidData) {
                AddOrEditAccountScreenUIErrorData()
            } else {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIErrorText.ACCOUNT_EXISTS
                )
            }
        }

        return isValidData
    }
}