package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.orDefault
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Stable
internal class AddTransactionScreenUIStateAndEvents(
    val state: AddTransactionScreenUIState,
    val events: AddTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    val setScreenBottomSheetType: (AddTransactionScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddTransactionScreenUIStateAndEvents(
    uiState: AddTransactionScreenUiStateData,
    uiVisibilityState: AddTransactionScreenUiVisibilityState,
    isCtaButtonEnabled: Boolean,
    filteredCategories: ImmutableList<Category>,
    titleSuggestions: ImmutableList<String>?,
    selectedTransactionType: TransactionType?,
    isDataFetchCompleted: Boolean,
    validTransactionTypesForNewTransaction: ImmutableList<TransactionType>,
    currentLocalDate: LocalDate,
    transactionForValues: ImmutableList<TransactionFor>,
    accounts: ImmutableList<Account>,
): AddTransactionScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: AddTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedAddTransactionScreenBottomSheetType
        }
    // endregion

    // region is transaction date picker dialog visible
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region is transaction time picker dialog visible
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        setIsTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
    ) {
        AddTransactionScreenUIStateAndEvents(
            state = AddTransactionScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                isLoading = false,
                uiState = uiState.orDefault(),
                uiVisibilityState = uiVisibilityState.orDefault(),
                isBottomSheetVisible = screenBottomSheetType != AddTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = isCtaButtonEnabled.orFalse(),
                appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
                accountFromTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_from
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                accountToTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_to
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                filteredCategories = filteredCategories.orEmpty(),
                transactionTypesForNewTransactionChipUIData = validTransactionTypesForNewTransaction.map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                }.toImmutableList().orEmpty(),
                titleSuggestions = titleSuggestions.orEmpty(),
                titleSuggestionsChipUIData = titleSuggestions.orEmpty()
                    .map { title ->
                        ChipUIData(
                            text = title,
                        )
                    }.toImmutableList(),
                accounts = accounts.orEmpty(),
                transactionForValuesChipUIData = transactionForValues.map { transactionFor ->
                    ChipUIData(
                        text = transactionFor.titleToDisplay,
                    )
                }.toImmutableList().orEmpty(),
                currentLocalDate = currentLocalDate.orMin(),
            ),
            events = AddTransactionScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
                setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddTransactionScreenBottomSheetType.None)
                },
            )
        )
    }
}
