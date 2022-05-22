package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyScrollableRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.dayOfMonth
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.hour
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

    val clearFocus = {
        state.focusManager.clearFocus()
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    LaunchedEffect(
        key1 = state.modalBottomSheetState,
    ) {
        if (state.modalBottomSheetState.isVisible) {
            keyboardController?.hide()
        }
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
                    clearFocus()
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
                    MyOutlinedTextField(
                        value = uiState.amount,
                        labelTextStringResourceId = R.string.screen_add_transaction_amount,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_amount,
                        onClickTrailingIcon = {
                            data.screenViewModel.clearAmount()
                        },
                        onValueChange = {
                            data.screenViewModel.updateAmount(
                                updatedAmount = it
                            )
                        },
                        visualTransformation = AmountCommaVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
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
                            value = uiState.category?.title.orEmpty(),
                            labelTextStringResourceId = R.string.screen_add_transaction_category,
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_CATEGORY
                                clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
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
                        MyOutlinedTextField(
                            value = uiState.title,
                            labelTextStringResourceId = R.string.screen_add_transaction_title,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_title,
                            onClickTrailingIcon = {
                                data.screenViewModel.clearTitle()
                            },
                            onValueChange = {
                                data.screenViewModel.updateTitle(
                                    updatedTitle = it
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    clearFocus()
                                },
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
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
                            clearFocus()
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
                        MyOutlinedTextField(
                            value = uiState.description,
                            labelTextStringResourceId = R.string.screen_add_transaction_description,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_description,
                            onClickTrailingIcon = {
                                data.screenViewModel.clearDescription()
                            },
                            onValueChange = {
                                data.screenViewModel.updateDescription(
                                    updatedDescription = it,
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    clearFocus()
                                },
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
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
                            value = uiState.sourceFrom?.name.orEmpty(),
                            labelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                                R.string.screen_add_transaction_source_from
                            } else {
                                R.string.screen_add_transaction_source
                            },
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_FROM
                                clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
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
                            value = uiState.sourceTo?.name.orEmpty(),
                            labelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                                R.string.screen_add_transaction_source_to
                            } else {
                                R.string.screen_add_transaction_source
                            },
                            onClick = {
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_TO
                                clearFocus()
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
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
                        value = uiState.transactionCalendar.formattedDate(),
                        labelTextStringResourceId = R.string.screen_add_transaction_transaction_date,
                        onClick = {
                            transactionDatePickerDialog.show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    MyReadOnlyTextField(
                        value = uiState.transactionCalendar.formattedTime(),
                        labelTextStringResourceId = R.string.screen_add_transaction_transaction_time,
                        onClick = {
                            transactionTimePickerDialog.show()
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
