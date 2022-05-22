package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyScrollableRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.OutlinedTextFieldLabelText
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.dayOfMonth
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.hour
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.minute
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.month
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.setDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.setTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.year
import java.util.Calendar

enum class AddTransactionBottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_SOURCE_FROM,
    SELECT_SOURCE_TO,
}

data class AddTransactionScreenViewData(
    val screenViewModel: AddTransactionScreenViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun AddTransactionScreenView(
    data: AddTransactionScreenViewData,
    state: AddTransactionScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val categories by data.screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val sources by data.screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val transactionTypesForNewTransaction by data.screenViewModel.transactionTypesForNewTransaction.collectAsState(
        initial = emptyList(),
    )
    val uiState by data.screenViewModel.uiState.collectAsState()
    val uiVisibilityState by data.screenViewModel.uiVisibilityState.collectAsState()
    val selectedTransactionType by data.screenViewModel.selectedTransactionType.collectAsState()
    val isValidTransactionData by data.screenViewModel.isValidTransactionData.collectAsState()
    val titleSuggestions by data.screenViewModel.titleSuggestions.collectAsState()

    val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        data.screenViewModel.updateTransactionCalendar(
            updatedTransactionCalendar = (uiState.transactionCalendar.clone() as Calendar)
                .setDate(
                    dayOfMonth = dayOfMonth,
                    month = month,
                    year = year,
                ),
        )

    }
    val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        data.screenViewModel.updateTransactionCalendar(
            updatedTransactionCalendar = (uiState.transactionCalendar.clone() as Calendar)
                .setTime(
                    hour = hour,
                    minute = minute,
                )
        )
    }
    val transactionDatePickerDialog = DatePickerDialog(
        state.context,
        onDateSetListener,
        uiState.transactionCalendar.year,
        uiState.transactionCalendar.month,
        uiState.transactionCalendar.dayOfMonth,
    )
    val transactionTimePickerDialog = TimePickerDialog(
        state.context,
        onTimeSetListener,
        uiState.transactionCalendar.hour,
        uiState.transactionCalendar.minute,
        false,
    )
    var addTransactionBottomSheetType by remember {
        mutableStateOf(
            value = AddTransactionBottomSheetType.NONE,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    LaunchedEffect(
        key1 = state.modalBottomSheetState,
    ) {
        keyboardController?.hide()
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = addTransactionBottomSheetType != AddTransactionBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
        }
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = if (state.modalBottomSheetState.currentValue == ModalBottomSheetValue.Expanded) {
            BottomSheetExpandedShape
        } else {
            BottomSheetShape
        },
        sheetContent = {
            when (addTransactionBottomSheetType) {
                AddTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                AddTransactionBottomSheetType.SELECT_CATEGORY -> {
                    AddTransactionSelectCategoryBottomSheet(
                        data = AddTransactionSelectCategoryBottomSheetData(
                            items = categories
                                .filter { category ->
                                    category.transactionType == selectedTransactionType
                                }
                                .map { category ->
                                    AddTransactionSelectCategoryBottomSheetItemData(
                                        category = category,
                                        isSelected = category.id == uiState.category?.id,
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.updateCategory(
                                                    updatedCategory = category,
                                                )
                                                addTransactionBottomSheetType =
                                                    AddTransactionBottomSheetType.NONE
                                            }
                                        },
                                    )
                                }
                                .toList(),
                        ),
                    )
                }
                AddTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    AddTransactionSelectSourceBottomSheet(
                        data = AddTransactionSelectSourceBottomSheetData(
                            items = sources
                                .map { source ->
                                    AddTransactionSelectSourceBottomSheetItemData(
                                        text = source.name,
                                        iconKey = source.type.title,
                                        isSelected = source.id == uiState.sourceFrom?.id,
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.updateSourceFrom(
                                                    updatedSourceFrom = source,
                                                )
                                                addTransactionBottomSheetType =
                                                    AddTransactionBottomSheetType.NONE
                                            }
                                        },
                                    )
                                }
                                .toList(),
                        ),
                    )
                }
                AddTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    AddTransactionSelectSourceBottomSheet(
                        data = AddTransactionSelectSourceBottomSheetData(
                            items = sources
                                .map { source ->
                                    AddTransactionSelectSourceBottomSheetItemData(
                                        text = source.name,
                                        iconKey = source.type.title,
                                        isSelected = source.id == uiState.sourceTo?.id,
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.updateSourceTo(
                                                    updatedSourceTo = source,
                                                )
                                                addTransactionBottomSheetType =
                                                    AddTransactionBottomSheetType.NONE
                                            }
                                        },
                                    )
                                }
                                .toList(),
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
                    titleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
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
                        items = transactionTypesForNewTransaction
                            .map { transactionType ->
                                MyRadioGroupItem(
                                    text = transactionType.title,
                                )
                            },
                        selectedItemIndex = uiState.selectedTransactionTypeIndex,
                        onSelectionChange = { index ->
                            data.screenViewModel.updateSelectedTransactionTypeIndex(
                                updatedSelectedTransactionTypeIndex = index
                            )
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    OutlinedTextField(
                        value = uiState.amount,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_amount,
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = uiState.amount.isNotNullOrBlank(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                MyIconButton(
                                    onClickLabel = stringResource(
                                        id = R.string.screen_add_transaction_clear_amount,
                                    ),
                                    onClick = {
                                        data.screenViewModel.clearAmount()
                                    },
                                    modifier = Modifier
                                        .padding(
                                            end = 4.dp,
                                        ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        tint = Color.DarkGray,
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_transaction_clear_amount,
                                        ),
                                    )
                                }
                            }
                        },
                        onValueChange = {
                            data.screenViewModel.updateAmount(
                                updatedAmount = it
                            )
                        },
                        visualTransformation = AmountCommaVisualTransformation(),
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
                            keyboardType = KeyboardType.NumberPassword,
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
                                vertical = 4.dp,
                            ),
                    )
                    AnimatedVisibility(
                        visible = uiVisibilityState.isCategoryTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = TextFieldValue(
                                text = uiState.category?.title.orEmpty(),
                            ),
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_CATEGORY
                                state.focusManager.clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
                            },
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = R.string.screen_add_transaction_category,
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    AnimatedVisibility(
                        visible = uiVisibilityState.isTitleTextFieldVisible,
                    ) {
                        OutlinedTextField(
                            value = uiState.title,
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = R.string.screen_add_transaction_title,
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = uiState.title.isNotNullOrBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    MyIconButton(
                                        onClickLabel = stringResource(
                                            id = R.string.screen_add_transaction_clear_title,
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
                                            tint = Color.DarkGray,
                                            contentDescription = stringResource(
                                                id = R.string.screen_add_transaction_clear_title,
                                            ),
                                        )
                                    }
                                }
                            },
                            onValueChange = {
                                data.screenViewModel.updateTitle(
                                    updatedTitle = it
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
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    MyScrollableRadioGroup(
                        items = titleSuggestions
                            .map { title ->
                                MyRadioGroupItem(
                                    text = title,
                                )
                            },
                        selectedItemIndex = null,
                        onSelectionChange = { index ->
                            data.screenViewModel.updateTitle(
                                updatedTitle = titleSuggestions[index]
                            )
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    AnimatedVisibility(
                        visible = uiVisibilityState.isTransactionForRadioGroupVisible,
                    ) {
                        MyRadioGroup(
                            items = data.screenViewModel.transactionForValues
                                .map { transactionFor ->
                                    MyRadioGroupItem(
                                        text = transactionFor.title,
                                    )
                                },
                            selectedItemIndex = uiState.selectedTransactionForIndex,
                            onSelectionChange = { index ->
                                data.screenViewModel.updateSelectedTransactionForIndex(
                                    updatedSelectedTransactionForIndex = index
                                )
                            },
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    AnimatedVisibility(
                        visible = uiVisibilityState.isDescriptionTextFieldVisible,
                    ) {
                        OutlinedTextField(
                            value = uiState.description,
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = R.string.screen_add_transaction_description,
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = uiState.description.isNotNullOrBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    MyIconButton(
                                        onClickLabel = stringResource(
                                            id = R.string.screen_add_transaction_clear_description,
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
                                            tint = Color.DarkGray,
                                            contentDescription = stringResource(
                                                id = R.string.screen_add_transaction_clear_description,
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
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    AnimatedVisibility(
                        visible = uiVisibilityState.isSourceFromTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = TextFieldValue(
                                text = uiState.sourceFrom?.name.orEmpty(),
                            ),
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_FROM
                                state.focusManager.clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
                            },
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                                        R.string.screen_add_transaction_source_from
                                    } else {
                                        R.string.screen_add_transaction_source
                                    },
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    AnimatedVisibility(
                        visible = uiVisibilityState.isSourceToTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = TextFieldValue(
                                text = uiState.sourceTo?.name.orEmpty(),
                            ),
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_TO
                                state.focusManager.clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
                            },
                            label = {
                                OutlinedTextFieldLabelText(
                                    textStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                                        R.string.screen_add_transaction_source_to
                                    } else {
                                        R.string.screen_add_transaction_source
                                    },
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    MyReadOnlyTextField(
                        value = TextFieldValue(
                            text = uiState.transactionCalendar.formattedDate(),
                        ),
                        onClick = {
                            transactionDatePickerDialog.show()
                        },
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_transaction_date,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    MyReadOnlyTextField(
                        value = TextFieldValue(
                            text = uiState.transactionCalendar.formattedTime(),
                        ),
                        onClick = {
                            transactionTimePickerDialog.show()
                        },
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_transaction_time,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    SaveButton(
                        textStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
                        isEnabled = isValidTransactionData,
                        onClick = {
                            data.screenViewModel.insertTransaction()
                        },
                    )
                }
            }
        }
    }
}
