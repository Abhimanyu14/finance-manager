package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Immutable
public sealed class CategoriesSetAsDefaultConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : CategoriesSetAsDefaultConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : CategoriesSetAsDefaultConfirmationBottomSheetEvent()
}

@Immutable
public data class CategoriesSetAsDefaultConfirmationBottomSheetData(
    val transactionType: TransactionType,
)

@Composable
public fun CategoriesSetAsDefaultConfirmationBottomSheet(
    data: CategoriesSetAsDefaultConfirmationBottomSheetData,
    handleEvent: (event: CategoriesSetAsDefaultConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.screen_categories_bottom_sheet_set_as_default_message,
                data.transactionType.title.lowercase(),
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_categories_bottom_sheet_set_as_default_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_categories_bottom_sheet_set_as_default_positive_button_text,
            ),
            title = stringResource(
                id = R.string.screen_categories_bottom_sheet_set_as_default_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
