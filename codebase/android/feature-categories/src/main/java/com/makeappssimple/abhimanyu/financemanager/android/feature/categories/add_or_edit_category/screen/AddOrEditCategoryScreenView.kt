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
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.components.AddOrEditCategorySelectEmojiBottomSheetContent

internal enum class AddOrEditCategoryBottomSheetType : BottomSheetType {
    NONE,
    SELECT_EMOJI,
}

@Immutable
internal data class AddOrEditCategoryScreenViewData(
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val selectedTransactionTypeIndex: Int,
    val emojiGroups: Map<String, List<Emoji>>,
    val transactionTypes: List<TransactionType>,
    val navigationManager: NavigationManager,
    val emoji: String,
    val searchText: String,
    val title: String,
    val clearTitle: () -> Unit,
    val isValidCategoryData: () -> Boolean,
    val onCtaButtonClick: () -> Unit,
    val updateEmoji: (updatedEmoji: String) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedIndex: Int) -> Unit,
    val updateTitle: (updatedTitle: String) -> Unit,
)

@Composable
internal fun AddOrEditCategoryScreenView(
    data: AddOrEditCategoryScreenViewData,
    state: CommonScreenViewState,
) {
    var addOrEditCategoryBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditCategoryBottomSheetType.NONE,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                addOrEditCategoryBottomSheetType = AddOrEditCategoryBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        addOrEditCategoryBottomSheetType = AddOrEditCategoryBottomSheetType.NONE
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetShape = when (addOrEditCategoryBottomSheetType) {
            AddOrEditCategoryBottomSheetType.NONE -> {
                BottomSheetShape
            }

            AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                BottomSheetExpandedShape
            }
        },
        sheetContent = {
            when (addOrEditCategoryBottomSheetType) {
                AddOrEditCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                    AddOrEditCategorySelectEmojiBottomSheetContent(
                        context = state.context,
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        emojiGroups = data.emojiGroups,
                        searchText = data.searchText,
                        resetBottomSheetType = {
                            addOrEditCategoryBottomSheetType = AddOrEditCategoryBottomSheetType.NONE
                        },
                        updateEmoji = { updatedEmoji ->
                            data.updateEmoji(updatedEmoji)
                        },
                        updateSearchText = { updatedSearchText ->
                            data.updateSearchText(updatedSearchText)
                        },
                    )
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = {
                    navigateUp(
                        navigationManager = data.navigationManager,
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
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
                items = data.transactionTypes
                    .map { transactionType ->
                        ChipItem(
                            text = transactionType.title,
                        )
                    },
                selectedItemIndex = data.selectedTransactionTypeIndex,
                onSelectionChange = { updatedIndex ->
                    data.updateSelectedTransactionTypeIndex(updatedIndex)
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
                    emoji = data.emoji,
                    onClick = {
                        state.keyboardController?.hide()
                        addOrEditCategoryBottomSheetType =
                            AddOrEditCategoryBottomSheetType.SELECT_EMOJI
                        toggleModalBottomSheetState(
                            coroutineScope = state.coroutineScope,
                            modalBottomSheetState = state.modalBottomSheetState,
                        )
                    },
                )
                MyOutlinedTextField(
                    value = data.title,
                    labelTextStringResourceId = R.string.screen_add_or_edit_category_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_category_clear_title,
                    onClickTrailingIcon = {
                        data.clearTitle()
                    },
                    onValueChange = { updatedTitle ->
                        data.updateTitle(updatedTitle)
                    },
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
                textStringResourceId = data.ctaButtonLabelTextStringResourceId,
                isEnabled = data.isValidCategoryData(),
                onClick = {
                    data.onCtaButtonClick()
                },
            )
        }
    }
}
