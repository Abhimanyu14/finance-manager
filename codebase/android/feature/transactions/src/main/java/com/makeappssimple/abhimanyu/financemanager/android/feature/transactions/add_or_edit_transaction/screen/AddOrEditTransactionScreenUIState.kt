package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.orDefault
import java.time.LocalDate

@Stable
class AddOrEditTransactionScreenUIState(
    data: MyResult<AddOrEditTransactionScreenUIData>?,
    private val unwrappedData: AddOrEditTransactionScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    isEdit: Boolean,
    val addOrEditTransactionBottomSheetType: AddOrEditTransactionBottomSheetType,
    val isTransactionDatePickerDialogVisible: Boolean,
    val isTransactionTimePickerDialogVisible: Boolean,
    val setAddOrEditTransactionBottomSheetType: (AddOrEditTransactionBottomSheetType) -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val uiState: AddOrEditTransactionScreenUiStateData = unwrappedData?.uiState.orDefault(),
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState =
        unwrappedData?.uiVisibilityState.orDefault(),
    val isCtaButtonEnabled: Boolean = unwrappedData?.isCtaButtonEnabled.orFalse(),
    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_appbar_title
    } else {
        R.string.screen_add_transaction_appbar_title
    },
    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_floating_action_button_content_description
    } else {
        R.string.screen_add_transaction_floating_action_button_content_description
    },
    @StringRes
    val accountFromTextFieldLabelTextStringResourceId: Int =
        if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_account_from
        } else {
            R.string.screen_add_or_edit_transaction_account
        },
    @StringRes
    val accountToTextFieldLabelTextStringResourceId: Int =
        if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_account_to
        } else {
            R.string.screen_add_or_edit_transaction_account
        },
    val filteredCategories: List<Category> = unwrappedData?.filteredCategories.orEmpty(),
    val transactionTypesForNewTransactionChipUIData: List<ChipUIData> =
        unwrappedData?.transactionTypesForNewTransaction?.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }.orEmpty(),
    val titleSuggestions: List<String> = unwrappedData?.titleSuggestions.orEmpty(),
    val titleSuggestionsChipUIData: List<ChipUIData> = titleSuggestions.map { title ->
        ChipUIData(
            text = title,
        )
    },
    val accounts: List<Account> = unwrappedData?.accounts.orEmpty(),
    val transactionForValuesChipUIData: List<ChipUIData> =
        unwrappedData?.transactionForValues?.map { transactionFor ->
            ChipUIData(
                text = transactionFor.titleToDisplay,
            )
        }.orEmpty(),
    val currentLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditTransactionBottomSheetType(AddOrEditTransactionBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberAddOrEditTransactionScreenUIState(
    data: MyResult<AddOrEditTransactionScreenUIData>?,
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
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }

    return remember(
        data,
        isEdit,
        addOrEditTransactionBottomSheetType,
        isTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setAddOrEditTransactionBottomSheetType,
        setIsTransactionDatePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
    ) {
        AddOrEditTransactionScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditTransactionBottomSheetType = addOrEditTransactionBottomSheetType,
            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
            setAddOrEditTransactionBottomSheetType = setAddOrEditTransactionBottomSheetType,
            setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
            setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
        )
    }
}
