package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal data class AddTransactionScreenUIState(
    val accountFrom: Account? = null,
    val accountFromText: AccountFromText = AccountFromText.Account,
    val accountTo: Account? = null,
    val accountToText: AccountToText = AccountToText.Account,
    val screenBottomSheetType: AddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
    val screenSnackbarType: AddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
    val uiVisibilityState: AddTransactionScreenUiVisibilityState = AddTransactionScreenUiVisibilityState.Expense,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isTransactionDatePickerDialogVisible: Boolean = false,
    val isTransactionTimePickerDialogVisible: Boolean = false,
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val selectedTransactionTypeIndex: Int? = null,
    val accounts: ImmutableList<Account> = persistentListOf(),
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val titleSuggestionsChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionForValuesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionTypesForNewTransactionChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val titleSuggestions: ImmutableList<String> = persistentListOf(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState

internal sealed class AccountFromText {
    data object AccountFrom : AccountFromText()
    data object Account : AccountFromText()
}

internal val AccountFromText.stringResourceId: Int
    get() {
        return when (this) {
            AccountFromText.AccountFrom -> R.string.screen_add_or_edit_transaction_account_from
            AccountFromText.Account -> R.string.screen_add_or_edit_transaction_account
        }
    }

internal sealed class AccountToText {
    data object AccountTo : AccountToText()
    data object Account : AccountToText()
}

internal val AccountToText.stringResourceId: Int
    get() {
        return when (this) {
            AccountToText.AccountTo -> R.string.screen_add_or_edit_transaction_account_to
            AccountToText.Account -> R.string.screen_add_or_edit_transaction_account
        }
    }
