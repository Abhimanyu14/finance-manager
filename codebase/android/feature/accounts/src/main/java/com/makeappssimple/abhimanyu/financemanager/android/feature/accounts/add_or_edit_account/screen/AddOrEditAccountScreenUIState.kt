package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

@Stable
class AddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    private val unwrappedData: AddOrEditAccountScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    isEdit: Boolean,
    setAddOrEditAccountBottomSheetType: (AddOrEditAccountBottomSheetType) -> Unit,
    val addOrEditAccountBottomSheetType: AddOrEditAccountBottomSheetType,
    val nameTextFieldFocusRequester: FocusRequester,
    val balanceAmountTextFieldFocusRequester: FocusRequester,
    val isLoading: Boolean = unwrappedData.isNull(),
    val isCtaButtonEnabled: Boolean = unwrappedData?.isValidAccountData.orFalse(),
    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_account_appbar_title
    } else {
        R.string.screen_add_account_appbar_title
    },
    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_account_floating_action_button_content_description
    } else {
        R.string.screen_add_account_floating_action_button_content_description
    },
    @StringRes
    val nameTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.errorData?.nameTextField?.textStringResourceId,
    val selectedAccountTypeIndex: Int = unwrappedData?.selectedAccountTypeIndex.orZero(),
    private val selectedAccount: AccountType? = unwrappedData?.accountTypes?.getOrNull(
        selectedAccountTypeIndex
    ),
    val accountTypesChipUIDataList: List<ChipUIData> = unwrappedData?.accountTypes
        ?.map { accountType ->
            ChipUIData(
                text = accountType.title,
                icon = accountType.icon,
            )
        }.orEmpty(),
    val balanceAmountValue: TextFieldValue = unwrappedData?.balanceAmountValue.orEmpty(),
    val minimumBalanceAmountValue: TextFieldValue =
        unwrappedData?.minimumBalanceAmountValue.orEmpty(),
    val name: TextFieldValue = unwrappedData?.name.orEmpty(),
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditAccountBottomSheetType(AddOrEditAccountBottomSheetType.NONE)
    },
    val visibilityData: AddOrEditAccountScreenUIVisibilityData = AddOrEditAccountScreenUIVisibilityData(
        balanceAmountTextField = isEdit,
        minimumBalanceAmountTextField = selectedAccount == AccountType.BANK,
        nameTextField = if (isEdit) {
            unwrappedData?.accountIsNotCash.orFalse()
        } else {
            true
        },
        nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
        accountTypesRadioGroup = if (isEdit) {
            unwrappedData?.accountIsNotCash.orFalse()
        } else {
            true
        },
    ),
) : ScreenUIState

@Composable
fun rememberAddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    isEdit: Boolean,
): AddOrEditAccountScreenUIState {
    val (addOrEditAccountBottomSheetType, setAddOrEditAccountBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditAccountBottomSheetType.NONE,
        )
    }
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }

    return remember(
        data,
        isEdit,
        addOrEditAccountBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        AddOrEditAccountScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditAccountBottomSheetType = addOrEditAccountBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            setAddOrEditAccountBottomSheetType = setAddOrEditAccountBottomSheetType,
        )
    }
}
