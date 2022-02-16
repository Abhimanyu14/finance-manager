package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiPickerBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiPickerBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyExtendedFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

data class AddCategoryScreenViewData(
    val screenViewModel: AddCategoryViewModel,
)

@ExperimentalMaterialApi
@Composable
fun AddCategoryScreenView(
    data: AddCategoryScreenViewData,
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val focusRequester = remember {
        FocusRequester()
    }

    // TODO-Abhi: Add check to restrict category name with text "default"

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            EmojiPickerBottomSheet(
                data = EmojiPickerBottomSheetData(
                    emojis = data.screenViewModel.emojis,
                    onEmojiSelection = { emoji ->
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            data.screenViewModel.emoji = emoji.character
                        }
                    },
                ),
            )
        },
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_category_appbar_title,
                            ),
                            color = Primary,
                        )
                    },
                    navigationIcon = {
                        NavigationBackButton(
                            navigationManager = data.screenViewModel.navigationManager,
                        )
                    },
                    modifier = Modifier
                        .background(
                            color = Surface,
                        ),
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    focusManager.clearFocus()
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
                    /*
                    TODO-Abhi: Emoji Picker
                    Box(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape,
                            )
                            .clickable {
                                toggleModalBottomSheetState(
                                    coroutineScope = coroutineScope,
                                    modalBottomSheetState = modalBottomSheetState,
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
                                    text = data.screenViewModel.emoji
                                    textSize = 28F
                                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                                }
                            },
                            update = {
                                it.apply {
                                    text = data.screenViewModel.emoji
                                }
                            },
                        )
                    }
                    */
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
                            selectedItemIndex = data.screenViewModel.selectedTransactionTypeIndex,
                            onSelectionChange = { index ->
                                data.screenViewModel.selectedTransactionTypeIndex = index
                            },
                            modifier = Modifier
                                .padding(
                                    all = 12.dp,
                                ),
                        )
                    }
                    OutlinedTextField(
                        value = data.screenViewModel.title,
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_category_title,
                                ),
                                color = Color.DarkGray,
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = data.screenViewModel.title.isNotNullOrBlank(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                MyIconButton(
                                    onClickLabel = stringResource(
                                        id = R.string.screen_add_category_clear_title,
                                    ),
                                    onClick = {
                                        data.screenViewModel.title = ""
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
                            data.screenViewModel.title = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                            onDone = {
                                focusManager.clearFocus()
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
                                focusRequester = focusRequester,
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                    OutlinedTextField(
                        value = data.screenViewModel.description,
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_category_description,
                                ),
                                color = Color.DarkGray,
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = data.screenViewModel.description.isNotNullOrBlank(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                MyIconButton(
                                    onClickLabel = stringResource(
                                        id = R.string.screen_add_category_clear_description,
                                    ),
                                    onClick = {
                                        data.screenViewModel.description = ""
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
                            data.screenViewModel.description = it
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
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
                    MyExtendedFloatingActionButton(
                        onClickLabel = stringResource(
                            id = R.string.screen_add_category_floating_action_button_content_description,
                        ),
                        enabled = data.screenViewModel.title.isNotNullOrBlank(),
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = Color.Transparent,
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (data.screenViewModel.title.isNotNullOrBlank()) {
                                Color.Transparent
                            } else {
                                LightGray
                            },
                        ),
                        onClick = {
                            data.screenViewModel.insertCategory()
                        },
                        modifier = Modifier
                            .padding(
                                all = 16.dp,
                            ),
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_category_floating_action_button_content_description,
                            ),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = if (data.screenViewModel.title.isNotNullOrBlank()) {
                                White
                            } else {
                                LightGray
                            },
                            modifier = Modifier
                                .defaultMinSize(
                                    minWidth = 100.dp,
                                ),
                        )
                    }
                }
            }
        }
    }
}
