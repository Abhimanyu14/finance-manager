package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.setDate
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.setTime
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyDatePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyTimePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingSuggestionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_category.SelectCategoryBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_source.SelectSourceBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import java.util.Calendar

internal enum class AddTransactionBottomSheetType : BottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_SOURCE_FROM,
    SELECT_SOURCE_TO,
}

@Immutable
internal data class AddTransactionScreenViewData(
    val uiState: AddTransactionScreenUiState,
    val uiVisibilityState: AddTransactionScreenUiVisibilityState,
    val isValidTransactionData: Boolean,
    val filteredCategories: List<Category>,
    val sources: List<Source>,
    val titleSuggestions: List<String>,
    val transactionTypesForNewTransaction: List<TransactionType>,
    val transactionForValues: List<TransactionFor>,
    val navigationManager: NavigationManager,
    val selectedTransactionType: TransactionType?,
    val clearAmount: () -> Unit,
    val clearDescription: () -> Unit,
    val clearTitle: () -> Unit,
    val insertTransaction: () -> Unit,
    val updateAmount: (updatedAmount: String) -> Unit,
    val updateCategory: (updatedCategory: Category?) -> Unit,
    val updateDescription: (updatedDescription: String) -> Unit,
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
    val updateSourceFrom: (updatedSourceFrom: Source?) -> Unit,
    val updateSourceTo: (updatedSourceTo: Source?) -> Unit,
    val updateTitle: (updatedTitle: String) -> Unit,
    val updateTransactionCalendar: (updatedTransactionCalendar: Calendar) -> Unit,
)

@Composable
internal fun AddTransactionScreenView(
    data: AddTransactionScreenViewData,
    state: CommonScreenViewState,
) {
    val transactionDatePickerDialog = getMyDatePickerDialog(
        context = state.context,
        calendar = data.uiState.transactionCalendar,
        onDateSetListener = { year, month, dayOfMonth ->
            data.updateTransactionCalendar(
                (data.uiState.transactionCalendar.clone() as Calendar)
                    .setDate(
                        dayOfMonth = dayOfMonth,
                        month = month,
                        year = year,
                    ),
            )
        },
    )
    val transactionTimePickerDialog = getMyTimePickerDialog(
        context = state.context,
        calendar = data.uiState.transactionCalendar,
        onTimeSetListener = { hour, minute ->
            data.updateTransactionCalendar(
                (data.uiState.transactionCalendar.clone() as Calendar)
                    .setTime(
                        hour = hour,
                        minute = minute,
                    )
            )
        },
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
            state.keyboardController?.hide()
        }
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = addTransactionBottomSheetType != AddTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
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
                    SelectCategoryBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        filteredCategories = data.filteredCategories,
                        selectedCategoryId = data.uiState.category?.id,
                        resetBottomSheetType = {
                            addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
                        },
                        updateCategory = { updatedCategory ->
                            data.updateCategory(updatedCategory)
                        },
                    )
                }
                AddTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    SelectSourceBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceFrom?.id,
                        resetBottomSheetType = {
                            addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
                        },
                        updateSource = { updatedSource ->
                            data.updateSourceFrom(updatedSource)
                        }
                    )
                }
                AddTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    SelectSourceBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceTo?.id,
                        resetBottomSheetType = {
                            addTransactionBottomSheetType = AddTransactionBottomSheetType.NONE
                        },
                        updateSource = { updatedSource ->
                            data.updateSourceTo(updatedSource)
                        }
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            MyScaffoldContentWrapper(
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
                    MyHorizontalScrollingRadioGroup(
                        items = data.transactionTypesForNewTransaction
                            .map { transactionType ->
                                ChipItem(
                                    text = transactionType.title,
                                )
                            },
                        selectedItemIndex = data.uiState.selectedTransactionTypeIndex,
                        onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                            data.updateSelectedTransactionTypeIndex(
                                updatedSelectedTransactionTypeIndex
                            )
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                    )
                    MyOutlinedTextField(
                        value = data.uiState.amount,
                        labelTextStringResourceId = R.string.screen_add_transaction_amount,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_amount,
                        onClickTrailingIcon = {
                            data.clearAmount()
                        },
                        onValueChange = { updatedAmount ->
                            data.updateAmount(updatedAmount)
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
                        visible = data.uiVisibilityState.isCategoryTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = data.uiState.category?.title.orEmpty(),
                            labelTextStringResourceId = R.string.screen_add_transaction_category,
                            onClick = {
                                clearFocus()
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_CATEGORY
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
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
                        visible = data.uiVisibilityState.isTitleTextFieldVisible,
                    ) {
                        MyOutlinedTextField(
                            value = data.uiState.title,
                            labelTextStringResourceId = R.string.screen_add_transaction_title,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_title,
                            onClickTrailingIcon = {
                                data.clearTitle()
                            },
                            onValueChange = { updatedTitle ->
                                data.updateTitle(updatedTitle)
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
                        visible = data.uiVisibilityState.isTitleSuggestionsVisible,
                    ) {
                        MyHorizontalScrollingSuggestionGroup(
                            items = data.titleSuggestions
                                .map { title ->
                                    ChipItem(
                                        text = title,
                                    )
                                },
                            onSelectionChange = { index ->
                                clearFocus()
                                data.updateTitle(data.titleSuggestions[index])
                            },
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                        )
                    }
                    AnimatedVisibility(
                        visible = data.uiVisibilityState.isTransactionForRadioGroupVisible,
                    ) {
                        MyRadioGroup(
                            items = data.transactionForValues
                                .map { transactionFor ->
                                    ChipItem(
                                        text = transactionFor.titleToDisplay,
                                    )
                                },
                            selectedItemIndex = data.uiState.selectedTransactionForIndex,
                            onSelectionChange = { updatedSelectedTransactionForIndex ->
                                clearFocus()
                                data.updateSelectedTransactionForIndex(
                                    updatedSelectedTransactionForIndex
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
                        visible = data.uiVisibilityState.isDescriptionTextFieldVisible,
                    ) {
                        MyOutlinedTextField(
                            value = data.uiState.description,
                            labelTextStringResourceId = R.string.screen_add_transaction_description,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_transaction_clear_description,
                            onClickTrailingIcon = {
                                data.clearDescription()
                            },
                            onValueChange = { updatedDescription ->
                                data.updateDescription(updatedDescription)
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
                        visible = data.uiVisibilityState.isSourceFromTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = data.uiState.sourceFrom?.name.orEmpty(),
                            labelTextStringResourceId = if (data.selectedTransactionType == TransactionType.TRANSFER) {
                                R.string.screen_add_transaction_source_from
                            } else {
                                R.string.screen_add_transaction_source
                            },
                            onClick = {
                                clearFocus()
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_FROM
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
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
                        visible = data.uiVisibilityState.isSourceToTextFieldVisible,
                    ) {
                        MyReadOnlyTextField(
                            value = data.uiState.sourceTo?.name.orEmpty(),
                            labelTextStringResourceId = if (data.selectedTransactionType == TransactionType.TRANSFER) {
                                R.string.screen_add_transaction_source_to
                            } else {
                                R.string.screen_add_transaction_source
                            },
                            onClick = {
                                clearFocus()
                                addTransactionBottomSheetType =
                                    AddTransactionBottomSheetType.SELECT_SOURCE_TO
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
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
                        value = data.uiState.transactionCalendar.formattedDate(),
                        labelTextStringResourceId = R.string.screen_add_transaction_transaction_date,
                        onClick = {
                            clearFocus()
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
                        value = data.uiState.transactionCalendar.formattedTime(),
                        labelTextStringResourceId = R.string.screen_add_transaction_transaction_time,
                        onClick = {
                            clearFocus()
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
                        isEnabled = data.isValidTransactionData,
                        onClick = {
                            clearFocus()
                            data.insertTransaction()
                        },
                    )
                }
            }
        }
    }
}
