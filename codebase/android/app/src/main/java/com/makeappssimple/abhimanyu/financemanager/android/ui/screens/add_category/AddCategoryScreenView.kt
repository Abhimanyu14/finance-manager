package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import android.view.View
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiPickerBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiPickerBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.OutlinedTextFieldLabelText
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

data class AddCategoryScreenViewData(
    val screenViewModel: AddCategoryViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun AddCategoryScreenView(
    data: AddCategoryScreenViewData,
    state: AddCategoryScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val description by data.screenViewModel.description.collectAsState()
    val title by data.screenViewModel.title.collectAsState()
    val selectedTransactionTypeIndex by data.screenViewModel.selectedTransactionTypeIndex.collectAsState()
    val emoji by data.screenViewModel.emoji.collectAsState()
    val emojis by data.screenViewModel.emojis.collectAsState()

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }
    // TODO-Abhi: Add check to restrict category name with text "default"

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            EmojiPickerBottomSheet(
                data = EmojiPickerBottomSheetData(
                    emojis = emojis,
                    onEmojiSelection = { emoji ->
                        toggleModalBottomSheetState(
                            coroutineScope = state.coroutineScope,
                            modalBottomSheetState = state.modalBottomSheetState,
                        ) {
                            data.screenViewModel.updateEmoji(
                                updatedEmoji = emoji.character,
                            )
                        }
                    },
                ),
            )
        },
    ) {
        Scaffold(
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_add_category_appbar_title,
                    ),
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
                    // TODO-Abhi: Emoji Picker
                    Box(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape,
                            )
                            .clickable {
                                keyboardController?.hide()
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
                    OutlinedTextField(
                        value = title,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_category_title,
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
                                        id = R.string.screen_add_category_clear_title,
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
                                            id = R.string.screen_add_category_clear_title,
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
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                    OutlinedTextField(
                        value = description,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_category_description,
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = description.isNotNullOrBlank(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                MyIconButton(
                                    onClickLabel = stringResource(
                                        id = R.string.screen_add_category_clear_description,
                                    ),
                                    onClick = {
                                        data.screenViewModel.clearDescription()
                                    },
                                    modifier = Modifier
                                        .padding(
                                            end = 4.dp,
                                        ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_category_clear_description,
                                        ),
                                    )
                                }
                            }
                        },
                        onValueChange = {
                            data.screenViewModel.updateDescription(
                                updatedDescription = it,
                            )
                        },
                        keyboardActions = KeyboardActions(
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
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                    SaveButton(
                        textStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
                        isEnabled = data.screenViewModel.isValidCategoryData(),
                        onClick = {
                            data.screenViewModel.insertCategory()
                        },
                    )
                }
            }
        }
    }
}
