package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.screen

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.components.AddCategorySelectEmojiBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Black
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.loadingCompletedEmoji

enum class AddCategoryBottomSheetType : BottomSheetType {
    NONE,
    SELECT_EMOJI,
}

data class AddCategoryScreenViewData(
    val selectedTransactionTypeIndex: Int,
    val emojis: List<Emoji>,
    val transactionTypes: List<TransactionType>,
    val navigationManager: NavigationManager,
    val emoji: String,
    val searchText: String,
    val title: String,
    val clearTitle: () -> Unit,
    val insertCategory: () -> Unit,
    val isValidCategoryData: () -> Boolean,
    val updateEmoji: (updatedEmoji: String) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedIndex: Int) -> Unit,
    val updateTitle: (updatedTitle: String) -> Unit,
)

@Composable
fun AddCategoryScreenView(
    data: AddCategoryScreenViewData,
    state: CommonScreenViewState,
) {
    var addCategoryBottomSheetType by remember {
        mutableStateOf(
            value = AddCategoryBottomSheetType.NONE,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    LaunchedEffect(
        key1 = data.emojis,
    ) {
        if (data.emojis.isNotEmpty()) {
            data.updateEmoji(loadingCompletedEmoji)
        }
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                addCategoryBottomSheetType = AddCategoryBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = addCategoryBottomSheetType != AddCategoryBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        addCategoryBottomSheetType = AddCategoryBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addCategoryBottomSheetType) {
                AddCategoryBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                AddCategoryBottomSheetType.SELECT_EMOJI -> {
                    AddCategorySelectEmojiBottomSheetContent(
                        context = state.context,
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        emojis = data.emojis,
                        searchText = data.searchText,
                        resetBottomSheetType = {
                            addCategoryBottomSheetType = AddCategoryBottomSheetType.NONE
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
                    titleTextStringResourceId = R.string.screen_add_category_appbar_title,
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
                            items = data.transactionTypes
                                .map { transactionType ->
                                    MyRadioGroupItem(
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
                                    state.keyboardController?.hide()
                                    addCategoryBottomSheetType =
                                        AddCategoryBottomSheetType.SELECT_EMOJI
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    )
                                }
                                .padding(
                                    all = 4.dp,
                                ),
                        ) {
                            AndroidView(
                                factory = { context ->
                                    AppCompatTextView(context).apply {
                                        setTextColor(Black.toArgb())
                                        text = data.emoji
                                        textSize = 28F
                                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                                    }
                                },
                                update = {
                                    it.apply {
                                        text = data.emoji
                                    }
                                },
                            )
                        }
                        MyOutlinedTextField(
                            value = data.title,
                            labelTextStringResourceId = R.string.screen_add_category_title,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_category_clear_title,
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
                        textStringResourceId = R.string.screen_add_category_floating_action_button_content_description,
                        isEnabled = data.isValidCategoryData(),
                        onClick = {
                            data.insertCategory()
                        },
                    )
                }
            }
        }
    }
}
