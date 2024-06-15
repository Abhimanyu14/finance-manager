package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class AddCategoryScreenUIStateAndStateEvents(
    val state: AddCategoryScreenUIState,
    val events: AddCategoryScreenUIStateEvents,
) : ScreenUIStateAndStateEvents

@Composable
internal fun rememberAddCategoryScreenUIStateAndEvents(
    categories: ImmutableList<Category>,
    validTransactionTypes: ImmutableList<TransactionType>,
): AddCategoryScreenUIStateAndStateEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: AddCategoryScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddCategoryScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType ->
            screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(AddCategoryScreenBottomSheetType.None)
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
    val clearTitle = {
        title = title.copy(
            text = "",
        )
    }
    // endregion

    // region title text field error
    var titleTextFieldErrorTextStringResourceId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setTitleTextFieldErrorTextStringResourceId =
        { updatedTitleTextFieldErrorTextStringResourceId: Int? ->
            titleTextFieldErrorTextStringResourceId = updatedTitleTextFieldErrorTextStringResourceId
        }
    // endregion

    // region selected transaction type index
    var selectedTransactionTypeIndex: Int by remember {
        mutableIntStateOf(
            value = validTransactionTypes.indexOf(
                element = TransactionType.EXPENSE,
            ),
        )
    }
    val setSelectedTransactionTypeIndex = { updatedSelectedTransactionTypeIndex: Int ->
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
    }
    // endregion

    // region search text
    var searchText: String by remember {
        mutableStateOf(
            value = "",
        )
    }
    val setSearchText = { updatedSearchText: String ->
        searchText = updatedSearchText
    }
    // endregion

    // region emoji
    var emoji: String by remember {
        mutableStateOf(
            value = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
        )
    }
    val setEmoji = { updatedEmoji: String ->
        emoji = updatedEmoji
    }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        title,
        setTitle,
        clearTitle,
        titleTextFieldErrorTextStringResourceId,
        setTitleTextFieldErrorTextStringResourceId,
        selectedTransactionTypeIndex,
        setSelectedTransactionTypeIndex,
        searchText,
        setSearchText,
        emoji,
        setEmoji,
        categories,
        validTransactionTypes,
    ) {
        titleTextFieldErrorTextStringResourceId = null
        val isCtaButtonEnabled = if (title.text.isBlank()) {
            false
        } else if (isDefaultIncomeCategory(
                category = title.text.trim(),
            ) || isDefaultExpenseCategory(
                category = title.text.trim(),
            ) || isDefaultInvestmentCategory(
                category = title.text.trim(),
            ) || categories.find {
                it.title.equalsIgnoringCase(
                    other = title.text.trim(),
                )
            }.isNotNull()
        ) {
            titleTextFieldErrorTextStringResourceId =
                R.string.screen_add_or_edit_category_error_category_exists
            false
        } else {
            true
        }

        AddCategoryScreenUIStateAndStateEvents(
            state = AddCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
                isLoading = validTransactionTypes.isEmpty(),
                isSupportingTextVisible = titleTextFieldErrorTextStringResourceId.isNotNull(),
                isCtaButtonEnabled = isCtaButtonEnabled,
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                },
                emoji = emoji,
                emojiSearchText = searchText,
                title = title,
                titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
                appBarTitleTextStringResourceId = R.string.screen_add_category_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
            ),
            events = AddCategoryScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setTitle = setTitle,
                clearTitle = clearTitle,
                setSelectedTransactionTypeIndex = setSelectedTransactionTypeIndex,
                setSearchText = setSearchText,
                setEmoji = setEmoji,
            ),
        )
    }
}
