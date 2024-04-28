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
    public val screenBottomSheetType: AddOrEditTransactionScreenBottomSheetType,
    public val isTransactionDatePickerDialogVisible: Boolean,
    public val isTransactionTimePickerDialogVisible: Boolean,
    public val setScreenBottomSheetType: (AddOrEditTransactionScreenBottomSheetType) -> Unit,
    public val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    public val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val uiState: AddOrEditTransactionScreenUiStateData = unwrappedData?.uiState.orDefault(),
    public val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState =
        unwrappedData?.uiVisibilityState.orDefault(),
    public val isCtaButtonEnabled: Boolean = unwrappedData?.isCtaButtonEnabled.orFalse(),
    @StringRes
    public val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_appbar_title
    } else {
        R.string.screen_add_transaction_appbar_title
    },
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_floating_action_button_content_description
    } else {
        R.string.screen_add_transaction_floating_action_button_content_description
    },
    @StringRes
    public val accountFromTextFieldLabelTextStringResourceId: Int =
        if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_account_from
        } else {
            R.string.screen_add_or_edit_transaction_account
        },
    @StringRes
    public val accountToTextFieldLabelTextStringResourceId: Int =
        if (unwrappedData?.selectedTransactionType == TransactionType.TRANSFER) {
            R.string.screen_add_or_edit_transaction_account_to
        } else {
            R.string.screen_add_or_edit_transaction_account
        },
    public val filteredCategories: List<Category> = unwrappedData?.filteredCategories.orEmpty(),
    public val transactionTypesForNewTransactionChipUIData: List<ChipUIData> =
        unwrappedData?.transactionTypesForNewTransaction?.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }.orEmpty(),
    public val titleSuggestions: List<String> = unwrappedData?.titleSuggestions.orEmpty(),
    public val titleSuggestionsChipUIData: List<ChipUIData> = titleSuggestions.map { title ->
        ChipUIData(
            text = title,
        )
    },
    public val accounts: List<Account> = unwrappedData?.accounts.orEmpty(),
    public val transactionForValuesChipUIData: List<ChipUIData> =
        unwrappedData?.transactionForValues?.map { transactionFor ->
            ChipUIData(
                text = transactionFor.titleToDisplay,
            )
        }.orEmpty(),
    public val currentLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AddOrEditTransactionScreenBottomSheetType.None)
    },
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
        AddOrEditTransactionScreenUIState(
            data = data,
            isEdit = isEdit,
            screenBottomSheetType = screenBottomSheetType,
            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
            setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
        )
    }
}
