package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class EditTransactionForScreenUIStateAndEvents(
    val state: EditTransactionForScreenUIState,
    val events: EditTransactionForScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberEditTransactionForScreenUIStateAndEvents(
    transactionForValues: ImmutableList<TransactionFor>,
    transactionFor: TransactionFor?,
): EditTransactionForScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: EditTransactionForScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditTransactionForScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditTransactionForScreenBottomSheetType: EditTransactionForScreenBottomSheetType ->
            screenBottomSheetType = updatedEditTransactionForScreenBottomSheetType
        }
    // endregion

    // region title
    var title: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setTitle = { updatedTitle: TextFieldValue ->
        title = updatedTitle
    }
    // endregion

    // region title text field error text string resource id
    var titleTextFieldErrorTextStringResourceId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        title,
        setTitle,
        titleTextFieldErrorTextStringResourceId,
        transactionForValues,
        transactionFor,
    ) {

        titleTextFieldErrorTextStringResourceId = null
        val isCtaButtonEnabled = if (title.text.isBlank()) {
            false
        } else if (title.text != transactionFor?.title && transactionForValues.find { transactionForValue ->
                transactionForValue.title.equalsIgnoringCase(
                    other = title.text,
                )
            }.isNotNull()
        ) {
            titleTextFieldErrorTextStringResourceId =
                R.string.screen_add_or_edit_transaction_for_error_exists
            false
        } else {
            true
        }

        EditTransactionForScreenUIStateAndEvents(
            state = EditTransactionForScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
                isLoading = false,
                isCtaButtonEnabled = isCtaButtonEnabled,
                appBarTitleTextStringResourceId = R.string.screen_edit_transaction_for_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_for_floating_action_button_content_description,
                title = title,
                titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
            ),
            events = EditTransactionForScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(EditTransactionForScreenBottomSheetType.None)
                },
                setTitle = setTitle,
            ),
        )
    }
}
