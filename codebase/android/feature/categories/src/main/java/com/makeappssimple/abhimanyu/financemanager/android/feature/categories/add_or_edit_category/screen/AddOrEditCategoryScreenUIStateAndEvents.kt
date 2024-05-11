package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

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
internal class AddOrEditCategoryScreenUIStateAndEvents(
    val state: AddOrEditCategoryScreenUIState,
    val events: AddOrEditCategoryScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddOrEditCategoryScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (AddOrEditCategoryScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddOrEditCategoryScreenUIStateAndEvents(
    data: MyResult<AddOrEditCategoryScreenUIData>?,
    isEdit: Boolean,
): AddOrEditCategoryScreenUIStateAndEvents {
    var screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditCategoryScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddOrEditCategoryScreenBottomSheetType: AddOrEditCategoryScreenBottomSheetType ->
            screenBottomSheetType = updatedAddOrEditCategoryScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: AddOrEditCategoryScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AddOrEditCategoryScreenUIStateAndEvents(
            state = AddOrEditCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddOrEditCategoryScreenBottomSheetType.None,
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
                appBarTitleTextStringResourceId = if (isEdit) {
                    R.string.screen_edit_category_appbar_title
                } else {
                    R.string.screen_add_category_appbar_title
                },
                ctaButtonLabelTextStringResourceId = if (isEdit) {
                    R.string.screen_edit_category_floating_action_button_content_description
                } else {
                    R.string.screen_add_category_floating_action_button_content_description
                },
            ),
            events = AddOrEditCategoryScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddOrEditCategoryScreenBottomSheetType.None)
                },
            ),
        )
    }
}
