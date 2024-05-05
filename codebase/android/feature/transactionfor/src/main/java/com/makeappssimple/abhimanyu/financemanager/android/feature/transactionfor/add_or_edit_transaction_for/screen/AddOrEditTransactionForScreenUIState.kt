package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Stable
public class AddOrEditTransactionForScreenUIState(
    public val screenBottomSheetType: AddOrEditTransactionForScreenBottomSheetType,
    public val isCtaButtonEnabled: Boolean?,
    public val isLoading: Boolean,
    @StringRes
    public val appBarTitleTextStringResourceId: Int,
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int,
    @StringRes
    public val titleTextFieldErrorTextStringResourceId: Int?,
    public val title: TextFieldValue?,
    public val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
public fun rememberAddOrEditTransactionForScreenUIState(
    data: MyResult<AddOrEditTransactionForScreenUIData>?,
    isEdit: Boolean,
): AddOrEditTransactionForScreenUIState {
    var screenBottomSheetType: AddOrEditTransactionForScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionForScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddOrEditTransactionForScreenBottomSheetType: AddOrEditTransactionForScreenBottomSheetType ->
            screenBottomSheetType = updatedAddOrEditTransactionForScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
    ) {
        val unwrappedData: AddOrEditTransactionForScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AddOrEditTransactionForScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            isCtaButtonEnabled = unwrappedData?.isValidTransactionForData,
            appBarTitleTextStringResourceId = if (isEdit) {
                R.string.screen_edit_transaction_for_appbar_title
            } else {
                R.string.screen_add_transaction_for_appbar_title
            },
            ctaButtonLabelTextStringResourceId = if (isEdit) {
                R.string.screen_edit_transaction_for_floating_action_button_content_description
            } else {
                R.string.screen_add_transaction_for_floating_action_button_content_description
            },
            title = unwrappedData?.title,
            titleTextFieldErrorTextStringResourceId = unwrappedData?.titleTextFieldError?.textStringResourceId,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(AddOrEditTransactionForScreenBottomSheetType.None)
            },
        )
    }
}
