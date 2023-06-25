package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import java.time.LocalDate

@Stable
class AddOrEditTransactionScreenUIState(
    data: AddOrEditTransactionScreenUIData,
    isEdit: Boolean,
    val addOrEditTransactionBottomSheetType: AddOrEditTransactionBottomSheetType,
    val isTransactionDatePickerDialogVisible: Boolean,
    val setAddOrEditTransactionBottomSheetType: (AddOrEditTransactionBottomSheetType) -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
) {
    val uiState: AddOrEditTransactionScreenUiStateData = data.uiState
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState = data.uiVisibilityState
    val isCtaButtonEnabled: Boolean = data.isCtaButtonEnabled

    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_appbar_title
    } else {
        R.string.screen_add_transaction_appbar_title
    }

    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_floating_action_button_content_description
    } else {
        R.string.screen_add_transaction_floating_action_button_content_description
    }

    @StringRes
    val sourceFromTextFieldLabelTextStringResourceId: Int =
        if (data.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_source_from
        } else {
            R.string.screen_add_or_edit_transaction_source
        }

    @StringRes
    val sourceToTextFieldLabelTextStringResourceId: Int =
        if (data.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_source_to
        } else {
            R.string.screen_add_or_edit_transaction_source
        }

    val filteredCategories: List<Category> = data.filteredCategories
    val transactionTypesForNewTransactionChipUIData: List<ChipUIData> =
        data.transactionTypesForNewTransaction.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }
    val titleSuggestions: List<String> = data.titleSuggestions
    val titleSuggestionsChipUIData: List<ChipUIData> = titleSuggestions.map { title ->
        ChipUIData(
            text = title,
        )
    }
    val sources: List<Source> = data.sources
    val transactionForValuesChipUIData: List<ChipUIData> =
        data.transactionForValues.map { transactionFor ->
            ChipUIData(
                text = transactionFor.titleToDisplay,
            )
        }
    val currentLocalDate: LocalDate = data.currentLocalDate
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditTransactionBottomSheetType(AddOrEditTransactionBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditTransactionScreenUIState(
    data: AddOrEditTransactionScreenUIData,
    isEdit: Boolean,
): AddOrEditTransactionScreenUIState {
    val (addOrEditTransactionBottomSheetType: AddOrEditTransactionBottomSheetType, setAddOrEditTransactionBottomSheetType: (AddOrEditTransactionBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = AddOrEditTransactionBottomSheetType.NONE,
        )
    }
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }

    return remember(
        data,
        isEdit,
        addOrEditTransactionBottomSheetType,
        isTransactionDatePickerDialogVisible,
        setAddOrEditTransactionBottomSheetType,
        setIsTransactionDatePickerDialogVisible,
    ) {
        AddOrEditTransactionScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditTransactionBottomSheetType = addOrEditTransactionBottomSheetType,
            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
            setAddOrEditTransactionBottomSheetType = setAddOrEditTransactionBottomSheetType,
            setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
        )
    }
}
