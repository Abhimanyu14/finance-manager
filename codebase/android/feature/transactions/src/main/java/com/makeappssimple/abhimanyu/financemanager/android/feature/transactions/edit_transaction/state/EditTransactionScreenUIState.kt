package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiVisibilityState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Stable
internal data class EditTransactionScreenUIState(
    val screenBottomSheetType: EditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
    val uiState: EditTransactionScreenUiStateData = EditTransactionScreenUiStateData(),
    val uiVisibilityState: EditTransactionScreenUiVisibilityState = EditTransactionScreenUiVisibilityState.Expense,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isTransactionDatePickerDialogVisible: Boolean = false,
    val isTransactionTimePickerDialogVisible: Boolean = false,
    @StringRes val accountFromTextFieldLabelTextStringResourceId: Int = -1,
    @StringRes val accountToTextFieldLabelTextStringResourceId: Int = -1,
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    val accounts: ImmutableList<Account> = persistentListOf(),
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val titleSuggestionsChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionForValuesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionTypesForNewTransactionChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val titleSuggestions: ImmutableList<String> = persistentListOf(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
) : ScreenUIState
