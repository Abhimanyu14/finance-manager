package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

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
internal class AddCategoryScreenUIStateAndEvents(
    val state: AddCategoryScreenUIState,
    val events: AddCategoryScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddCategoryScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (AddCategoryScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddCategoryScreenUIStateAndEvents(
    data: MyResult<AddCategoryScreenUIData>?,
): AddCategoryScreenUIStateAndEvents {
    var screenBottomSheetType: AddCategoryScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddCategoryScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType ->
            screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: AddCategoryScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AddCategoryScreenUIStateAndEvents(
            state = AddCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
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
                appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
            ),
            events = AddCategoryScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddCategoryScreenBottomSheetType.None)
                },
            ),
        )
    }
}
