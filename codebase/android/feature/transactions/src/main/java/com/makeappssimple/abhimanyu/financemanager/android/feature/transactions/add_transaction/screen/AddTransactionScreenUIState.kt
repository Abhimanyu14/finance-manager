package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Stable
internal data class AddTransactionScreenUIState(
    val screenBottomSheetType: AddTransactionScreenBottomSheetType,
    val uiState: AddTransactionScreenUiStateData,
    val uiVisibilityState: AddTransactionScreenUiVisibilityState,
    val isBottomSheetVisible: Boolean,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val isTransactionDatePickerDialogVisible: Boolean,
    val isTransactionTimePickerDialogVisible: Boolean,
    @StringRes val accountFromTextFieldLabelTextStringResourceId: Int,
    @StringRes val accountToTextFieldLabelTextStringResourceId: Int,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val accounts: ImmutableList<Account>,
    val filteredCategories: ImmutableList<Category>,
    val titleSuggestionsChipUIData: ImmutableList<ChipUIData>,
    val transactionForValuesChipUIData: ImmutableList<ChipUIData>,
    val transactionTypesForNewTransactionChipUIData: ImmutableList<ChipUIData>,
    val titleSuggestions: ImmutableList<String>,
    val currentLocalDate: LocalDate,
    val selectedTransactionType: TransactionType,
) : ScreenUIState
