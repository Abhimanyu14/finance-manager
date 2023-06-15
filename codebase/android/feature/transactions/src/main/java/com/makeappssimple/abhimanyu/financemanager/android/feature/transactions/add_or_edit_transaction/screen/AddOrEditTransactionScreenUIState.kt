package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState

@Stable
class AddOrEditTransactionScreenUIState(
    data: AddOrEditTransactionScreenUIData,
    isEdit: Boolean,
    val setAddOrEditTransactionBottomSheetType: (AddOrEditTransactionBottomSheetType) -> Unit,
    val addOrEditTransactionBottomSheetType: AddOrEditTransactionBottomSheetType,
) {
    val uiState: AddOrEditTransactionScreenUiState = data.uiState
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState = data.uiVisibilityState
    val isCtaButtonEnabled: Boolean = data.isCtaButtonEnabled
    val filteredCategories: List<Category> = data.filteredCategories
    val sources: List<Source> = data.sources
    val titleSuggestions: List<String> = data.titleSuggestions
    val currentTimeMillis: Long = data.currentTimeMillis
    val selectedTransactionType: TransactionType? = data.selectedTransactionType

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
    val transactionTypesForNewTransactionChipUIData =
        data.transactionTypesForNewTransaction.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }
    val titleSuggestionsChipUIData = titleSuggestions.map { title ->
        ChipUIData(
            text = title,
        )
    }
    val transactionForValuesChipUIData = data.transactionForValues.map { transactionFor ->
        ChipUIData(
            text = transactionFor.titleToDisplay,
        )
    }

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

    return remember(
        data,
        isEdit,
        addOrEditTransactionBottomSheetType,
        setAddOrEditTransactionBottomSheetType,
    ) {
        AddOrEditTransactionScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditTransactionBottomSheetType = addOrEditTransactionBottomSheetType,
            setAddOrEditTransactionBottomSheetType = setAddOrEditTransactionBottomSheetType,
        )
    }
}
