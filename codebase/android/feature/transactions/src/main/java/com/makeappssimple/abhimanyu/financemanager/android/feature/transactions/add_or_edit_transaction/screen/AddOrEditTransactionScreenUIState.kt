package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val resetScreenBottomSheetType: () -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    val setScreenBottomSheetType: (AddOrEditTransactionScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberAddOrEditTransactionScreenUIState(
    data: MyResult<AddOrEditTransactionScreenUIData>?,
    isEdit: Boolean,
): AddOrEditTransactionScreenUIState {
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    var screenBottomSheetType: AddOrEditTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddOrEditTransactionScreenBottomSheetType: AddOrEditTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedAddOrEditTransactionScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setScreenBottomSheetType,
        setIsTransactionDatePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
    ) {
        val unwrappedData: AddOrEditTransactionScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val titleSuggestions = unwrappedData?.titleSuggestions.orEmpty()

        // TODO(Abhi): Can be reordered to match the class ordering
        AddOrEditTransactionScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
            setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
            isLoading = unwrappedData.isNull(),
            uiState = unwrappedData?.uiState.orDefault(),
            uiVisibilityState = unwrappedData?.uiVisibilityState.orDefault(),
            isBottomSheetVisible = screenBottomSheetType != AddOrEditTransactionScreenBottomSheetType.None,
            isCtaButtonEnabled = unwrappedData?.isCtaButtonEnabled.orFalse(),
            appBarTitleTextStringResourceId = if (isEdit) {
                R.string.screen_edit_transaction_appbar_title
            } else {
                R.string.screen_add_transaction_appbar_title
            },
            ctaButtonLabelTextStringResourceId = if (isEdit) {
                R.string.screen_edit_transaction_floating_action_button_content_description
            } else {
                R.string.screen_add_transaction_floating_action_button_content_description
            },
            accountFromTextFieldLabelTextStringResourceId =
            if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
                R.string.screen_add_or_edit_transaction_account_from
            } else {
                R.string.screen_add_or_edit_transaction_account
            },
            accountToTextFieldLabelTextStringResourceId =
            if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
                R.string.screen_add_or_edit_transaction_account_to
            } else {
                R.string.screen_add_or_edit_transaction_account
            },
            filteredCategories = unwrappedData?.filteredCategories.orEmpty(),
            transactionTypesForNewTransactionChipUIData = unwrappedData?.transactionTypesForNewTransaction?.map { transactionType ->
                ChipUIData(
                    text = transactionType.title,
                )
            }.orEmpty(),
            titleSuggestions = unwrappedData?.titleSuggestions.orEmpty(),
            titleSuggestionsChipUIData = titleSuggestions.map { title ->
                ChipUIData(
                    text = title,
                )
            },
            accounts = unwrappedData?.accounts.orEmpty(),
            transactionForValuesChipUIData =
            unwrappedData?.transactionForValues?.map { transactionFor ->
                ChipUIData(
                    text = transactionFor.titleToDisplay,
                )
            }.orEmpty(),
            currentLocalDate = unwrappedData?.currentLocalDate.orMin(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(AddOrEditTransactionScreenBottomSheetType.None)
            },
        )
    }
}
