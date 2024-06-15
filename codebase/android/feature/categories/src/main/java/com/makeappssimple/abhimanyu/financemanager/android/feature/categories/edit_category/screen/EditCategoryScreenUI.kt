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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.EditCategorySelectEmojiBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircleEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event.EditCategoryScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import kotlinx.coroutines.delay

@Composable
internal fun EditCategoryScreenUI(
    uiState: EditCategoryScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: EditCategoryScreenUIEvent) -> Unit = {},
) {
    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            delay(300) // Source - https://stackoverflow.com/a/72783456/9636037
            state.focusRequester.requestFocus()
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_CATEGORY,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                EditCategoryScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                EditCategoryScreenBottomSheetType.SelectEmoji -> {
                    EditCategorySelectEmojiBottomSheet(
                        searchText = uiState.emojiSearchText,
                        resetBottomSheetType = {
                            handleUIEvent(EditCategoryScreenUIEvent.OnBottomSheetDismissed)
                        },
                        updateEmoji = { updatedEmoji ->
                            handleUIEvent(
                                EditCategoryScreenUIEvent.OnEmojiUpdated(
                                    updatedEmoji = updatedEmoji,
                                )
                            )
                        },
                        updateSearchText = { updatedSearchText ->
                            handleUIEvent(
                                EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated(
                                    updatedSearchText = updatedSearchText,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.screenBottomSheetType) {
            is EditCategoryScreenBottomSheetType.None -> {
                BottomSheetShape
            }

            is EditCategoryScreenBottomSheetType.SelectEmoji -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = {
                    handleUIEvent(EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(EditCategoryScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            MyRadioGroup(
                data = MyRadioGroupData(
                    isLoading = uiState.isLoading,
                    items = uiState.transactionTypesChipUIData,
                    selectedItemIndex = uiState.selectedTransactionTypeIndex,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyRadioGroupEvent.OnSelectionChange -> {
                            handleUIEvent(
                                EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated(
                                    updatedIndex = event.index,
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
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
                    data = MyEmojiCircleData(
                        isClickable = true,
                        isLoading = uiState.isLoading,
                        emojiCircleSize = EmojiCircleSize.Normal,
                        emoji = uiState.emoji,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyEmojiCircleEvent.OnClick -> {
                                handleUIEvent(EditCategoryScreenUIEvent.OnEmojiCircleClick)
                            }
                        }
                    },
                )
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = state.focusRequester,
                        )
                        .padding(
                            start = 8.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.title,
                        labelTextStringResourceId = R.string.screen_add_or_edit_category_title,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_category_clear_title,
                        supportingText = if (uiState.isSupportingTextVisible) {
                            {
                                uiState.titleTextFieldErrorTextStringResourceId?.let { titleTextFieldErrorTextStringResourceId ->
                                    MyText(
                                        text = stringResource(
                                            id = titleTextFieldErrorTextStringResourceId,
                                        ),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.error,
                                        ),
                                    )
                                }
                            }
                        } else {
                            null
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
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(EditCategoryScreenUIEvent.OnClearTitleButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    EditCategoryScreenUIEvent.OnTitleUpdated(
                                        updatedTitle = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled.orFalse(),
                    isLoading = uiState.isLoading,
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(EditCategoryScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
