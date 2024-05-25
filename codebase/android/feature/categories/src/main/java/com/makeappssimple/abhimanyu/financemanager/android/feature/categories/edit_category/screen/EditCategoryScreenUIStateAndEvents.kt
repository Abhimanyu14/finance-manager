package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Stable
internal class EditCategoryScreenUIStateAndEvents(
    val state: EditCategoryScreenUIState,
    val events: EditCategoryScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class EditCategoryScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (EditCategoryScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberEditCategoryScreenUIStateAndEvents(
    data: MyResult<EditCategoryScreenUIData>?,
): EditCategoryScreenUIStateAndEvents {
    var screenBottomSheetType: EditCategoryScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditCategoryScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType ->
            screenBottomSheetType = updatedEditCategoryScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: EditCategoryScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        EditCategoryScreenUIStateAndEvents(
            state = EditCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
                isLoading = unwrappedData.isNull(),
                isSupportingTextVisible = unwrappedData?.titleTextFieldError?.textStringResourceId.isNotNull(),
                isCtaButtonEnabled = unwrappedData?.isCtaButtonEnabled.orFalse(),
                selectedTransactionTypeIndex = unwrappedData?.selectedTransactionTypeIndex,
                transactionTypesChipUIData = unwrappedData?.validTransactionTypes?.map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                }.orEmpty(),
                emoji = unwrappedData?.emoji.orEmpty(),
                emojiSearchText = unwrappedData?.emojiSearchText.orEmpty(),
                title = unwrappedData?.title.orEmpty(),
                titleTextFieldErrorTextStringResourceId = unwrappedData?.titleTextFieldError?.textStringResourceId,
                appBarTitleTextStringResourceId = R.string.screen_edit_category_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_category_floating_action_button_content_description,
            ),
            events = EditCategoryScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(EditCategoryScreenBottomSheetType.None)
                },
            ),
        )
    }
}
