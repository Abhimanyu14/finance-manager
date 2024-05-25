package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Stable
internal class EditTransactionForScreenUIStateAndEvents(
    val state: EditTransactionForScreenUIState,
    val events: EditTransactionForScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class EditTransactionForScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberEditTransactionForScreenUIStateAndEvents(
    data: MyResult<EditTransactionForScreenUIData>?,
    isEdit: Boolean,
): EditTransactionForScreenUIStateAndEvents {
    var screenBottomSheetType: EditTransactionForScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditTransactionForScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditTransactionForScreenBottomSheetType: EditTransactionForScreenBottomSheetType ->
            screenBottomSheetType = updatedEditTransactionForScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
    ) {
        val unwrappedData: EditTransactionForScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        EditTransactionForScreenUIStateAndEvents(
            state = EditTransactionForScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
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
            ),
            events = EditTransactionForScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(EditTransactionForScreenBottomSheetType.None)
                },
            ),
        )
    }
}
