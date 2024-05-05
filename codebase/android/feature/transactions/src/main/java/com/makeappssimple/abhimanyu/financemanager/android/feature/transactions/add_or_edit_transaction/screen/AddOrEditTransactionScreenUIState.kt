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
public class AddOrEditTransactionScreenUIState(
    public val screenBottomSheetType: AddOrEditTransactionScreenBottomSheetType,
    public val uiState: AddOrEditTransactionScreenUiStateData,
    public val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    public val isCtaButtonEnabled: Boolean,
    public val isLoading: Boolean,
    public val isTransactionDatePickerDialogVisible: Boolean,
    public val isTransactionTimePickerDialogVisible: Boolean,
    @StringRes
    public val accountFromTextFieldLabelTextStringResourceId: Int,
    @StringRes
    public val accountToTextFieldLabelTextStringResourceId: Int,
    @StringRes
    public val appBarTitleTextStringResourceId: Int,
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int,
    public val accounts: List<Account>,
    public val filteredCategories: List<Category>,
    public val titleSuggestionsChipUIData: List<ChipUIData>,
    public val transactionForValuesChipUIData: List<ChipUIData>,
    public val transactionTypesForNewTransactionChipUIData: List<ChipUIData>,
    public val titleSuggestions: List<String>,
    public val currentLocalDate: LocalDate,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    public val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    public val setScreenBottomSheetType: (AddOrEditTransactionScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
public fun rememberAddOrEditTransactionScreenUIState(
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
