package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.component.bottomsheet.AddOrEditCategorySelectEmojiBottomSheet

enum class AddOrEditCategoryBottomSheetType : BottomSheetType {
    NONE,
    SELECT_EMOJI,
}

@Immutable
data class AddOrEditCategoryScreenUIData(
    val isValidCategoryData: Boolean = false,
    val selectedTransactionTypeIndex: Int = 0,
    val transactionTypes: List<TransactionType> = emptyList(),
    val emoji: String = "",
    val searchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
)

@Immutable
sealed class AddOrEditCategoryScreenUIEvent {
    object ClearTitle : AddOrEditCategoryScreenUIEvent()
    object NavigateUp : AddOrEditCategoryScreenUIEvent()
    object OnCtaButtonClick : AddOrEditCategoryScreenUIEvent()

    data class UpdateEmoji(
        val updatedEmoji: String,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateSearchText(
        val updatedSearchText: String,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateSelectedTransactionTypeIndex(
        val updatedIndex: Int,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditCategoryScreenUIEvent()
}

@Composable
internal fun AddOrEditCategoryScreenUI(
    uiState: AddOrEditCategoryScreenUIState,
    state: CommonScreenUIState,
    handleUIEvents: (uiEvent: AddOrEditCategoryScreenUIEvent) -> Unit,
) {
    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            state.focusRequester.requestFocus()
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        bottomSheetType = uiState.addOrEditCategoryBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = {
            uiState.resetBottomSheetType()
            handleUIEvents(
                AddOrEditCategoryScreenUIEvent.UpdateSearchText(
                    updatedSearchText = "",
                )
            )
        },
    )

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.addOrEditCategoryBottomSheetType) {
                AddOrEditCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                    AddOrEditCategorySelectEmojiBottomSheet(
                        context = state.context,
                        searchText = uiState.searchText,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        updateEmoji = { updatedEmoji ->
                            handleUIEvents(
                                AddOrEditCategoryScreenUIEvent.UpdateEmoji(
                                    updatedEmoji = updatedEmoji,
                                )
                            )
                        },
                        updateSearchText = { updatedSearchText ->
                            handleUIEvents(
                                AddOrEditCategoryScreenUIEvent.UpdateSearchText(
                                    updatedSearchText = updatedSearchText,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        sheetShape = when (uiState.addOrEditCategoryBottomSheetType) {
            AddOrEditCategoryBottomSheetType.NONE -> {
                BottomSheetShape
            }

            AddOrEditCategoryBottomSheetType.SELECT_EMOJI -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = {
                    handleUIEvents(AddOrEditCategoryScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        backHandlerEnabled = uiState.addOrEditCategoryBottomSheetType != AddOrEditCategoryBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                events = MyRadioGroupEvents(
                    onSelectionChange = { updatedIndex ->
                        handleUIEvents(
                            AddOrEditCategoryScreenUIEvent.UpdateSelectedTransactionTypeIndex(
                                updatedIndex = updatedIndex,
                            )
                        )
                    },
                ),
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
                        isLoading = uiState.isLoading,
                        emojiCircleSize = EmojiCircleSize.Normal,
                        emoji = uiState.emoji,
                    ),
                    events = MyEmojiCircleEvents(
                        onClick = {
                            uiState.setAddOrEditCategoryBottomSheetType(
                                AddOrEditCategoryBottomSheetType.SELECT_EMOJI
                            )
                        },
                    ),
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
                    events = MyOutlinedTextFieldEvents(
                        onClickTrailingIcon = {
                            handleUIEvents(AddOrEditCategoryScreenUIEvent.ClearTitle)
                        },
                        onValueChange = { updatedTitle ->
                            handleUIEvents(
                                AddOrEditCategoryScreenUIEvent.UpdateTitle(
                                    updatedTitle = updatedTitle,
                                )
                            )
                        },
                    ),
                )
            }
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isValidCategoryData.orFalse(),
                    isLoading = uiState.isLoading,
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                events = SaveButtonEvents(
                    onClick = {
                        handleUIEvents(AddOrEditCategoryScreenUIEvent.OnCtaButtonClick)
                    },
                ),
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
