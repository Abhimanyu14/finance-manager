package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmptySpace
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.OutlinedTextFieldLabelText
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.dayOfMonth
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
    val screenViewModel: AddTransactionViewModel,
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
    val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        data.screenViewModel.transactionCalendar =
            (data.screenViewModel.transactionCalendar.clone() as Calendar)
                .setDate(
                    dayOfMonth = dayOfMonth,
                    month = month,
                    year = year,
                )
    }
    val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        data.screenViewModel.transactionCalendar =
            (data.screenViewModel.transactionCalendar.clone() as Calendar)
                .setTime(
                    hour = hour,
                    minute = minute,
                )
    }
    val transactionDatePickerDialog = DatePickerDialog(
        state.context,
        onDateSetListener,
        data.screenViewModel.transactionCalendar.year,
        data.screenViewModel.transactionCalendar.month,
        data.screenViewModel.transactionCalendar.dayOfMonth,
    )
    val transactionTimePickerDialog = TimePickerDialog(
        state.context,
        onTimeSetListener,
        data.screenViewModel.transactionCalendar.hour,
        data.screenViewModel.transactionCalendar.minute,
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
        key1 = categories,
    ) {
        data.screenViewModel.expenseDefaultCategory = categories.firstOrNull {
            it.title.contains(
                other = "Default",
                ignoreCase = true,
            )
        }
        data.screenViewModel.incomeDefaultCategory = categories.firstOrNull {
            it.title.contains(
                other = "Salary",
                ignoreCase = true,
            )
        }

        data.screenViewModel.category = data.screenViewModel.expenseDefaultCategory
    }

    LaunchedEffect(
        key1 = state.modalBottomSheetState,
        block = {
            keyboardController?.hide()
        },
    )

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (addTransactionBottomSheetType) {
                AddTransactionBottomSheetType.NONE -> {
                    EmptySpace()
                }
                AddTransactionBottomSheetType.SELECT_CATEGORY -> {
                    AddTransactionSelectCategoryBottomSheet(
                        data = AddTransactionSelectCategoryBottomSheetData(
                            items = categories
                                .filter { category ->
                                    category.transactionType == data.screenViewModel.transactionTypes[data.screenViewModel.selectedTransactionTypeIndex]
                                }
                                .map { category ->
                                    AddTransactionSelectCategoryBottomSheetItemData(
                                        text = category.title,
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.category = category
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
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.sourceFrom = source
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
                                        onClick = {
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            ) {
                                                data.screenViewModel.sourceTo = source
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
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_add_transaction_appbar_title,
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
                    MyRadioGroup(
                        items = data.screenViewModel.getTransactionTypesForNewTransaction()
                            .map { transactionType ->
                                MyRadioGroupItem(
                                    text = transactionType.title,
                                )
                            },
                        selectedItemIndex = data.screenViewModel.selectedTransactionTypeIndex,
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
                        value = data.screenViewModel.amount,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_amount,
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = data.screenViewModel.amount.isNotNullOrBlank(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                MyIconButton(
                                    onClickLabel = stringResource(
                                        id = R.string.screen_add_transaction_clear_amount,
                                    ),
                                    onClick = {
                                        data.screenViewModel.amount = ""
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
                            data.screenViewModel.amount = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                state.focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Next,
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
                    OutlinedTextField(
                        value = data.screenViewModel.title,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_title,
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
                                        id = R.string.screen_add_transaction_clear_title,
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
                                        tint = Color.DarkGray,
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_transaction_clear_title,
                                        ),
                                    )
                                }
                            }
                        },
                        onValueChange = {
                            data.screenViewModel.title = it
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
                    AnimatedVisibility(
                        visible = data.screenViewModel.isCategoryTextFieldVisible(),
                    ) {
                        MyReadOnlyTextField(
                            value = data.screenViewModel.categoryTextFieldValue,
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
                        visible = data.screenViewModel.isTransactionForRadioGroupVisible(),
                    ) {
                        MyRadioGroup(
                            items = data.screenViewModel.transactionForValues
                                .map { transactionFor ->
                                    MyRadioGroupItem(
                                        text = transactionFor.title,
                                    )
                                },
                            selectedItemIndex = data.screenViewModel.selectedTransactionForIndex,
                            onSelectionChange = { index ->
                                data.screenViewModel.selectedTransactionForIndex = index
                            },
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    OutlinedTextField(
                        value = data.screenViewModel.description,
                        label = {
                            OutlinedTextFieldLabelText(
                                textStringResourceId = R.string.screen_add_transaction_description,
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
                                        id = R.string.screen_add_transaction_clear_description,
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
                                        tint = Color.DarkGray,
                                        contentDescription = stringResource(
                                            id = R.string.screen_add_transaction_clear_description,
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
                    AnimatedVisibility(
                        visible = data.screenViewModel.isSourceFromTextFieldVisible(),
                    ) {
                        MyReadOnlyTextField(
                            value = data.screenViewModel.sourceFromTextFieldValue,
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
                                    textStringResourceId = if (data.screenViewModel.transactionTypes[data.screenViewModel.selectedTransactionTypeIndex] == TransactionType.TRANSFER) {
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
                        visible = data.screenViewModel.isSourceToTextFieldVisible(),
                    ) {
                        MyReadOnlyTextField(
                            value = data.screenViewModel.sourceToTextFieldValue,
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
                                    textStringResourceId = if (data.screenViewModel.transactionTypes[data.screenViewModel.selectedTransactionTypeIndex] == TransactionType.TRANSFER) {
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
                        value = data.screenViewModel.transactionDateTextFieldValue,
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
                        value = data.screenViewModel.transactionTimeTextFieldValue,
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
                        isEnabled = data.screenViewModel.isValidTransactionData(),
                        onClick = {
                            data.screenViewModel.insertTransaction()
                        },
                    )
                }
            }
        }
    }
}
