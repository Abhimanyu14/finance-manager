package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.component.bottomsheet.AddOrEditCategorySelectEmojiBottomSheetContent

enum class AddOrEditCategoryBottomSheetType : BottomSheetType {
    NONE,
    SELECT_EMOJI,
}

@Immutable
data class AddOrEditCategoryScreenUIData(
    val isValidCategoryData: Boolean = false,
    @StringRes val appBarTitleTextStringResourceId: Int = 0,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = 0,
    val selectedTransactionTypeIndex: Int = 0,
    val emojiGroups: Map<String, List<Emoji>> = emptyMap(),
    val transactionTypes: List<TransactionType> = emptyList(),
    val emoji: String = "",
    val searchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
)

@Immutable
internal data class AddOrEditCategoryScreenUIEvents(
    val clearTitle: () -> Unit,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateEmoji: (updatedEmoji: String) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedIndex: Int) -> Unit,
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit,
)

@Composable
internal fun AddOrEditCategoryScreenUI(
    events: AddOrEditCategoryScreenUIEvents,
    uiState: AddOrEditCategoryScreenUIState,
    state: CommonScreenUIState,
) {
    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        bottomSheetType = uiState.addOrEditCategoryBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.addOrEditCategoryBottomSheetType) {
            AddOrEditCategoryBottomSheetType.NONE -> {
                BottomSheetShape
            }

            AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                BottomSheetExpandedShape
            }
        },
        sheetContent = {
            when (uiState.addOrEditCategoryBottomSheetType) {
                AddOrEditCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                    AddOrEditCategorySelectEmojiBottomSheetContent(
                        context = state.context,
                        emojiGroups = uiState.emojiGroups,
                        searchText = uiState.searchText,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        updateEmoji = { updatedEmoji ->
                            events.updateEmoji(updatedEmoji)
                        },
                    ) { updatedSearchText ->
                        events.updateSearchText(updatedSearchText)
                    }
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            MyRadioGroup(
                items = uiState.transactionTypes
                    .map { transactionType ->
                        ChipUIData(
                            text = transactionType.title,
                        )
                    },
                selectedItemIndex = uiState.selectedTransactionTypeIndex,
                onSelectionChange = { updatedIndex ->
                    events.updateSelectedTransactionTypeIndex(updatedIndex)
                },
                modifier = Modifier
                    .padding(
                        all = 12.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                MyEmojiCircle(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    emoji = uiState.emoji,
                    onClick = {
                        uiState.setAddOrEditCategoryBottomSheetType(AddOrEditCategoryBottomSheetType.SELECT_EMOJI)
                    },
                )
                MyOutlinedTextField(
                    textFieldValue = uiState.title,
                    labelTextStringResourceId = R.string.screen_add_or_edit_category_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_category_clear_title,
                    onClickTrailingIcon = events.clearTitle,
                    onValueChange = events.updateTitle,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            state.focusManager.moveFocus(
                                focusDirection = FocusDirection.Down,
                            )
                        },
                        onDone = {
                            state.focusManager.clearFocus()
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = state.focusRequester,
                        )
                        .padding(
                            start = 4.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                        ),
                )
            }
            SaveButton(
                textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                isEnabled = uiState.isValidCategoryData,
                onClick = events.onCtaButtonClick,
            )
        }
    }
}
