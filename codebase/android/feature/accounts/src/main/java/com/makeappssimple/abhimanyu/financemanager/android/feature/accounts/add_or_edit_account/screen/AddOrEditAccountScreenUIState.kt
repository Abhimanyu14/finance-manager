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
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

@Stable
public class AddOrEditAccountScreenUIState(
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
    setScreenBottomSheetType: (AddOrEditAccountScreenBottomSheetType) -> Unit,
    public val screenBottomSheetType: AddOrEditAccountScreenBottomSheetType,
    public val nameTextFieldFocusRequester: FocusRequester,
    public val balanceAmountTextFieldFocusRequester: FocusRequester,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val isCtaButtonEnabled: Boolean = unwrappedData?.isValidAccountData.orFalse(),
    @StringRes
    public val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_account_appbar_title
    } else {
        R.string.screen_add_account_appbar_title
    },
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_account_floating_action_button_content_description
    } else {
        R.string.screen_add_account_floating_action_button_content_description
    },
    @StringRes
    public val nameTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.errorData?.nameTextField?.textStringResourceId,
    public val selectedAccountTypeIndex: Int = unwrappedData?.selectedAccountTypeIndex.orZero(),
    private val selectedAccount: AccountType? = unwrappedData?.accountTypes?.getOrNull(
        selectedAccountTypeIndex
    ),
    public val accountTypesChipUIDataList: List<ChipUIData> = unwrappedData?.accountTypes
        ?.map { accountType ->
            ChipUIData(
                text = accountType.title,
                icon = accountType.icon,
            )
        }.orEmpty(),
    public val balanceAmountValue: TextFieldValue = unwrappedData?.balanceAmountValue.orEmpty(),
    public val minimumBalanceAmountValue: TextFieldValue =
        unwrappedData?.minimumBalanceAmountValue.orEmpty(),
    public val name: TextFieldValue = unwrappedData?.name.orEmpty(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AddOrEditAccountScreenBottomSheetType.NONE)
    },
    public val visibilityData: AddOrEditAccountScreenUIVisibilityData = AddOrEditAccountScreenUIVisibilityData(
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
public fun rememberAddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    isEdit: Boolean,
): AddOrEditAccountScreenUIState {
    val (screenBottomSheetType, setScreenBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditAccountScreenBottomSheetType.NONE,
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
        screenBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        AddOrEditAccountScreenUIState(
            data = data,
            isEdit = isEdit,
            screenBottomSheetType = screenBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
