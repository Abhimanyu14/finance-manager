package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.orDefault
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Stable
internal class EditTransactionScreenUIStateAndEvents(
    val state: EditTransactionScreenUIState,
    val events: EditTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberEditTransactionScreenUIStateAndEvents(
    uiState: EditTransactionScreenUiStateData,
    uiVisibilityState: EditTransactionScreenUiVisibilityState,
    isCtaButtonEnabled: Boolean,
    filteredCategories: ImmutableList<Category>,
    titleSuggestions: ImmutableList<String>?,
    selectedTransactionType: TransactionType?,
    isDataFetchCompleted: Boolean,
    validTransactionTypesForNewTransaction: ImmutableList<TransactionType>,
    currentLocalDate: LocalDate,
    transactionForValues: ImmutableList<TransactionFor>,
    accounts: ImmutableList<Account>,
): EditTransactionScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: EditTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedEditTransactionScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(EditTransactionScreenBottomSheetType.None)
    }
    // endregion

    // region is transaction date picker dialog visible
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    // endregion

    // region is transaction time picker dialog visible
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        setIsTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
        uiState,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        titleSuggestions,
        selectedTransactionType,
        isDataFetchCompleted,
        validTransactionTypesForNewTransaction,
        currentLocalDate,
        transactionForValues,
        accounts,
    ) {
        EditTransactionScreenUIStateAndEvents(
            state = EditTransactionScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                isLoading = false,
                uiState = uiState.orDefault(),
                uiVisibilityState = uiVisibilityState.orDefault(),
                isBottomSheetVisible = screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = isCtaButtonEnabled.orFalse(),
                appBarTitleTextStringResourceId = R.string.screen_edit_transaction_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_floating_action_button_content_description,
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
                titleSuggestionsChipUIData = titleSuggestions?.map { title ->
                    ChipUIData(
                        text = title,
                    )
                }?.toImmutableList().orEmpty(),
                accounts = accounts.orEmpty(),
                transactionForValuesChipUIData = transactionForValues.map { transactionFor ->
                    ChipUIData(
                        text = transactionFor.titleToDisplay,
                    )
                }.toImmutableList().orEmpty(),
                currentLocalDate = currentLocalDate.orMin(),
            ),
            events = EditTransactionScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
                setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
                resetScreenBottomSheetType = resetScreenBottomSheetType,
            )
        )
    }
}
