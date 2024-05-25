package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.orDefault
import kotlinx.collections.immutable.toImmutableList

@Stable
internal class EditTransactionScreenUIStateAndEvents(
    val state: EditTransactionScreenUIState,
    val events: EditTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class EditTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    val setScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberEditTransactionScreenUIStateAndEvents(
    data: MyResult<EditTransactionScreenUIData>?,
): EditTransactionScreenUIStateAndEvents {
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    var screenBottomSheetType: EditTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedEditTransactionScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setScreenBottomSheetType,
        setIsTransactionDatePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
    ) {
        val unwrappedData: EditTransactionScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val titleSuggestions = unwrappedData?.titleSuggestions.orEmpty()

        // TODO(Abhi): Can be reordered to match the class ordering
        EditTransactionScreenUIStateAndEvents(
            state = EditTransactionScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                isLoading = unwrappedData.isNull(),
                uiState = unwrappedData?.uiState.orDefault(),
                uiVisibilityState = unwrappedData?.uiVisibilityState.orDefault(),
                isBottomSheetVisible = screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = unwrappedData?.isCtaButtonEnabled.orFalse(),
                appBarTitleTextStringResourceId = R.string.screen_edit_transaction_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_floating_action_button_content_description,
                accountFromTextFieldLabelTextStringResourceId = if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_from
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                accountToTextFieldLabelTextStringResourceId = if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_to
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                filteredCategories = unwrappedData?.filteredCategories.orEmpty(),
                transactionTypesForNewTransactionChipUIData = unwrappedData?.transactionTypesForNewTransaction?.map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                }?.toImmutableList().orEmpty(),
                titleSuggestions = unwrappedData?.titleSuggestions.orEmpty(),
                titleSuggestionsChipUIData = titleSuggestions.map { title ->
                    ChipUIData(
                        text = title,
                    )
                }.toImmutableList(),
                accounts = unwrappedData?.accounts.orEmpty(),
                transactionForValuesChipUIData = unwrappedData?.transactionForValues?.map { transactionFor ->
                    ChipUIData(
                        text = transactionFor.titleToDisplay,
                    )
                }?.toImmutableList().orEmpty(),
                currentLocalDate = unwrappedData?.currentLocalDate.orMin(),
            ),
            events = EditTransactionScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
                setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(EditTransactionScreenBottomSheetType.None)
                },
            )
        )
    }
}
