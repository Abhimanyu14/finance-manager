package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import java.time.LocalDate

@Stable
internal class AddOrEditTransactionScreenUIState(
    val screenBottomSheetType: AddOrEditTransactionScreenBottomSheetType,
    val uiState: AddOrEditTransactionScreenUiStateData,
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    val isBottomSheetVisible: Boolean,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val isTransactionDatePickerDialogVisible: Boolean,
    val isTransactionTimePickerDialogVisible: Boolean,
    @StringRes val accountFromTextFieldLabelTextStringResourceId: Int,
    @StringRes val accountToTextFieldLabelTextStringResourceId: Int,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val accounts: List<Account>,
    val filteredCategories: List<Category>,
    val titleSuggestionsChipUIData: List<ChipUIData>,
    val transactionForValuesChipUIData: List<ChipUIData>,
    val transactionTypesForNewTransactionChipUIData: List<ChipUIData>,
    val titleSuggestions: List<String>,
    val currentLocalDate: LocalDate,
) : ScreenUIState
