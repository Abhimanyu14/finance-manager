package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category

import android.view.View
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.EmojiPickerBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.EmojiPickerBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.OutlinedTextFieldLabelText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.loadingCompletedEmoji
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.loadingEmoji
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

enum class EditCategoryBottomSheetType {
    NONE,
    SELECT_EMOJI,
}

data class EditCategoryScreenViewData(
    val screenViewModel: EditCategoryScreenViewModel,
    val categoryId: Int?,
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@ExperimentalMaterialApi
@Composable
fun EditCategoryScreenView(
    data: EditCategoryScreenViewData,
    state: EditCategoryScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val title by data.screenViewModel.title.collectAsState()
    val selectedTransactionTypeIndex by data.screenViewModel.selectedTransactionTypeIndex.collectAsState()
    val emoji by data.screenViewModel.emoji.collectAsState()
    val searchText by data.screenViewModel.searchText.collectAsState()
    val emojis by data.screenViewModel.filteredEmojis.collectAsState(
        initial = emptyList(),
    )
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
    LaunchedEffect(
        key1 = emojis,
    ) {
        if (emojis.isNotEmpty()) {
            if (emoji == loadingEmoji) {
                data.screenViewModel.updateEmoji(
                    updatedEmoji = loadingCompletedEmoji,
                )
            }
        }
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = editCategoryBottomSheetType != EditCategoryBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
        }
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (editCategoryBottomSheetType) {
                EditCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                EditCategoryBottomSheetType.SELECT_EMOJI -> {
                    EmojiPickerBottomSheet(
                        data = EmojiPickerBottomSheetData(
                            emojis = emojis,
                            searchText = searchText,
                            onEmojiClick = { emoji ->
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    editCategoryBottomSheetType = EditCategoryBottomSheetType.NONE
                                    data.screenViewModel.updateEmoji(
                                        updatedEmoji = emoji.character,
                                    )
                                }
                            },
                            onEmojiLongClick = { emoji ->
                                Toast.makeText(
                                    state.context,
                                    emoji.unicodeName.capitalizeWords(),
                                    Toast.LENGTH_SHORT,
                                ).show()
                            },
                            updateSearchText = { updatedSearchText ->
                                data.screenViewModel.updateSearchText(updatedSearchText)
                            }
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
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
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        MyRadioGroup(
                            items = data.screenViewModel.transactionTypes
                                .map { transactionType ->
                                    MyRadioGroupItem(
                                        text = transactionType.title,
                                    )
                                },
                            selectedItemIndex = selectedTransactionTypeIndex,
                            onSelectionChange = { index ->
                                data.screenViewModel.updateSelectedTransactionTypeIndex(
                                    updatedIndex = index,
                                )
                            },
                            modifier = Modifier
                                .padding(
                                    all = 12.dp,
                                ),
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                            )
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    keyboardController?.hide()
                                    editCategoryBottomSheetType =
                                        EditCategoryBottomSheetType.SELECT_EMOJI
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    ) {}
                                }
                                .padding(
                                    all = 4.dp,
                                ),
                        ) {
                            AndroidView(
                                factory = { context ->
                                    AppCompatTextView(context).apply {
                                        setTextColor(Color.Black.toArgb())
                                        text = emoji
                                        textSize = 28F
                                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                                    }
                                },
                                update = {
                                    it.apply {
                                        text = emoji
                                    }
                                },
                            )
                        }
                        OutlinedTextField(
                            value = title,
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = R.string.screen_edit_category_title,
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = title.isNotNullOrBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    MyIconButton(
                                        onClickLabel = stringResource(
                                            id = R.string.screen_edit_category_clear_title,
                                        ),
                                        onClick = {
                                            data.screenViewModel.clearTitle()
                                        },
                                        modifier = Modifier
                                            .padding(
                                                end = 4.dp,
                                            ),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Clear,
                                            contentDescription = stringResource(
                                                id = R.string.screen_edit_category_clear_title,
                                            ),
                                        )
                                    }
                                }
                            },
                            onValueChange = {
                                data.screenViewModel.updateTitle(
                                    updatedTitle = it,
                                )
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
                            singleLine = true,
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
                        isEnabled = data.screenViewModel.isValidCategoryData(),
                        onClick = {
                            data.screenViewModel.updateCategory()
                        },
                    )
                }
            }
        }
    }
}
