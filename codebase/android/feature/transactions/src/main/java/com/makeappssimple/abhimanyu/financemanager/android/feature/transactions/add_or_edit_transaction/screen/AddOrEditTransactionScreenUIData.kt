package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Immutable
public data class AddOrEditTransactionScreenUIData(
    val uiState: AddOrEditTransactionScreenUiStateData = AddOrEditTransactionScreenUiStateData(),
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState = AddOrEditTransactionScreenUiVisibilityState.Expense,
    val isCtaButtonEnabled: Boolean = false,
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val accounts: ImmutableList<Account> = persistentListOf(),
    val titleSuggestions: ImmutableList<String> = persistentListOf(),
    val transactionTypesForNewTransaction: ImmutableList<TransactionType> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val selectedTransactionType: TransactionType? = null,
) : ScreenUIData
