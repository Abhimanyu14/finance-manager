package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.components.EditCategorySelectEmojiBottomSheetContent

enum class EditCategoryBottomSheetType : BottomSheetType {
    NONE,
    SELECT_EMOJI,
}

data class EditCategoryScreenViewData(
    val categoryId: Int?,
    val selectedTransactionTypeIndex: Int,
    val emojis: List<Emoji>,
    val transactionTypes: List<TransactionType>,
    val navigationManager: NavigationManager,
    val emoji: String,
    val searchText: String,
    val title: String,
    val clearTitle: () -> Unit,
    val isValidCategoryData: () -> Boolean,
    val updateCategory: () -> Unit,
    val updateEmoji: (updatedEmoji: String) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedIndex: Int) -> Unit,
    val updateTitle: (updatedTitle: String) -> Unit,
)

@Composable
fun EditCategoryScreenView(
    data: EditCategoryScreenViewData,
    state: CommonScreenViewState,
) {
    var editCategoryBottomSheetType by remember {
        mutableStateOf(
            value = EditCategoryBottomSheetType.NONE,
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
                editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = editCategoryBottomSheetType != EditCategoryBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (editCategoryBottomSheetType) {
                EditCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                EditCategoryBottomSheetType.SELECT_EMOJI -> {
                    EditCategorySelectEmojiBottomSheetContent(
                        context = state.context,
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        emojis = data.emojis,
                        searchText = data.searchText,
                        resetBottomSheetType = {
                            editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
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
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.navigationManager,
                    titleTextStringResourceId = R.string.screen_edit_category_appbar_title,
                    isNavigationIconVisible = true,
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
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
                                MyRadioGroupItem(
                                    text = transactionType.title,
                                )
                            },
                        selectedItemIndex = data.selectedTransactionTypeIndex,
                        onSelectionChange = { index ->
                            data.updateSelectedTransactionTypeIndex(index)
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
                        EmojiCircle(
                            emoji = data.emoji,
                            emojiCircleSize = EmojiCircleSize.Normal,
                            onClick = {
                                state.keyboardController?.hide()
                                editCategoryBottomSheetType =
                                    EditCategoryBottomSheetType.SELECT_EMOJI
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                )
                            },
                        )
                        MyOutlinedTextField(
                            value = data.title,
                            labelTextStringResourceId = R.string.screen_edit_category_title,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_category_clear_title,
                            onClickTrailingIcon = {
                                data.clearTitle()
                            },
                            onValueChange = {
                                data.updateTitle(it)
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
                        textStringResourceId = R.string.screen_edit_category_floating_action_button_content_description,
                        isEnabled = data.isValidCategoryData(),
                        onClick = {
                            data.updateCategory()
                        },
                    )
                }
            }
        }
    }
}
