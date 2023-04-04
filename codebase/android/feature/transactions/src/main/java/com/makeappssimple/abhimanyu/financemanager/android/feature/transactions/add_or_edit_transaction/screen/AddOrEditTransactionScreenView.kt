package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyDatePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyTimePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_category.SelectCategoryBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_source.SelectSourceBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.components.action.ActionView
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenUiVisibilityState
import java.time.LocalDate
import java.time.LocalTime

internal enum class AddOrEditTransactionBottomSheetType : BottomSheetType {
    NONE,
    SELECT_CATEGORY,
    SELECT_SOURCE_FROM,
    SELECT_SOURCE_TO,
}

@Immutable
internal data class AddOrEditTransactionScreenViewData(
    val uiState: AddOrEditTransactionScreenUiState,
    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    val isCtaButtonEnabled: Boolean,
    val isScanVisible: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val filteredCategories: List<Category>,
    val sources: List<Source>,
    val titleSuggestions: List<String>,
    val transactionTypesForNewTransaction: List<TransactionType>,
    val transactionForValues: List<TransactionFor>,
    val navigationManager: NavigationManager,
    val selectedTransactionType: TransactionType?,
    val uriData: String,
    val clearAmount: () -> Unit,
    val clearDescription: () -> Unit,
    val clearTitle: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateAmount: (updatedAmount: TextFieldValue) -> Unit,
    val updateCategory: (updatedCategory: Category?) -> Unit,
    val updateDescription: (updatedDescription: TextFieldValue) -> Unit,
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
    val updateSourceFrom: (updatedSourceFrom: Source?) -> Unit,
    val updateSourceTo: (updatedSourceTo: Source?) -> Unit,
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit,
    val updateTransactionDate: (updatedTransactionDate: LocalDate) -> Unit,
    val updateTransactionTime: (updatedTransactionTime: LocalTime) -> Unit,
    val updateUriData: (updatedDataUri: String) -> Unit,
)

@Composable
internal fun AddOrEditTransactionScreenView(
    data: AddOrEditTransactionScreenViewData,
    state: CommonScreenViewState,
) {
    val context = LocalContext.current
    val barcodeScanningResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        data.updateUriData(it.data?.extras?.getString("value").orEmpty())
    }
    val upiPaymentResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            data.onCtaButtonClick()
        }
    }

    val transactionDatePickerDialog = getMyDatePickerDialog(
        context = state.context,
        currentDate = data.uiState.transactionDate,
        onDateSetListener = {
            data.updateTransactionDate(it)
        },
    )
    val transactionTimePickerDialog = getMyTimePickerDialog(
        context = state.context,
        currentTime = data.uiState.transactionTime,
        onTimeSetListener = {
            data.updateTransactionTime(it)
        },
    )

    var addOrEditTransactionBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionBottomSheetType.NONE,
        )
    }
    val transactionTypesForNewTransactionChipItems = remember(
        key1 = data.transactionTypesForNewTransaction,
    ) {
        data.transactionTypesForNewTransaction
            .map { transactionType ->
                ChipItem(
                    text = transactionType.title,
                )
            }
    }
    val titleSuggestionsChipItems = remember(
        key1 = data.titleSuggestions,
    ) {
        data.titleSuggestions
            .map { title ->
                ChipItem(
                    text = title,
                )
            }
    }
    val transactionForValuesChipItems = remember(
        key1 = data.transactionForValues,
    ) {
        data.transactionForValues
            .map { transactionFor ->
                ChipItem(
                    text = transactionFor.titleToDisplay,
                )
            }
    }

    val clearFocus = {
        state.focusManager.clearFocus()
    }

    val startUpiPayment: (
        amount: Double,
        uriString: String,
    ) -> Unit =
        startUpiPayment@{ amount: Double, uriString: String ->
            val amountString = "%.2f".format(amount)
            val uri = Uri.parse("${uriString}&am=$amountString")

            // Comment line - if you want to open specific application then you can pass that package name
            // For example if you want to open Bhim app then pass Bhim app package name
            // val packageManager: PackageManager = packageManager
            // Intent intent = packageManager.getLaunchIntentForPackage("com.mgs.induspsp")

            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                this.data = uri
            }
            val chooser = Intent.createChooser(intent, "Pay with...")
            upiPaymentResultLauncher.launch(chooser)
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
                addOrEditTransactionBottomSheetType = AddOrEditTransactionBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addOrEditTransactionBottomSheetType) {
                AddOrEditTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionBottomSheetType.SELECT_CATEGORY -> {
                    SelectCategoryBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        filteredCategories = data.filteredCategories,
                        selectedCategoryId = data.uiState.category?.id,
                        resetBottomSheetType = {
                            addOrEditTransactionBottomSheetType =
                                AddOrEditTransactionBottomSheetType.NONE
                        },
                        updateCategory = { updatedCategory ->
                            data.updateCategory(updatedCategory)
                        },
                    )
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM -> {
                    SelectSourceBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceFrom?.id,
                        resetBottomSheetType = {
                            addOrEditTransactionBottomSheetType =
                                AddOrEditTransactionBottomSheetType.NONE
                        },
                        updateSource = { updatedSource ->
                            data.updateSourceFrom(updatedSource)
                        }
                    )
                }

                AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO -> {
                    SelectSourceBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sources = data.sources,
                        selectedSourceId = data.uiState.sourceTo?.id,
                        resetBottomSheetType = {
                            addOrEditTransactionBottomSheetType =
                                AddOrEditTransactionBottomSheetType.NONE
                        },
                        updateSource = { updatedSource ->
                            data.updateSourceTo(updatedSource)
                        }
                    )
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = {
                    navigateUp(
                        navigationManager = data.navigationManager,
                    )
                },
            )
        },
        onClick = {
            clearFocus()
        },
        backHandlerEnabled = addOrEditTransactionBottomSheetType != AddOrEditTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            addOrEditTransactionBottomSheetType = AddOrEditTransactionBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            AnimatedVisibility(
                visible = data.isScanVisible,
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 32.dp,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                    ),
                ) {
                    ActionView(
                        textStringResourceId = R.string.screen_add_or_edit_transaction_scan,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    ) {
                        val uri = Uri.parse("makeappssimple://barcodes/scan_barcode/?deeplink=true")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        val packageManager: PackageManager = context.packageManager
                        val activities = packageManager.queryIntentActivities(intent, 0)
                        val isIntentSafe = activities.size > 0
                        if (isIntentSafe) {
                            barcodeScanningResultLauncher.launch(intent)
                        } else {
                            // TODO-Abhi: Show info message to user
                        }
                    }
                    /*
                    HomeActionView(
                        textStringResourceId = R.string.screen_home_pay,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    ) {
                        navigateToAddTransactionScreen(
                            navigationManager = data.navigationManager,
                        )
                    }
                    */
                }
            }
            AnimatedVisibility(
                visible = data.uiVisibilityState.isTransactionTypesRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    items = transactionTypesForNewTransactionChipItems,
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
            }
            MyOutlinedTextField(
                textFieldValue = data.uiState.amount,
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_amount,
                trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_amount,
                onClickTrailingIcon = {
                    data.clearAmount()
                },
                onValueChange = { updatedAmount ->
                    data.updateAmount(updatedAmount)
                },
                supportingText = {
                    AnimatedVisibility(
                        data.uiState.amountErrorText.isNotNullOrBlank(),
                    ) {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_add_or_edit_transaction_amount_error_text,
                                data.uiState.amountErrorText.orEmpty(),
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.error,
                            ),
                        )
                    }
                },
                isError = data.uiState.amountErrorText != null,
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
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_category,
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_CATEGORY
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
                    textFieldValue = data.uiState.title,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_title,
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
                visible = data.uiVisibilityState.isTitleSuggestionsVisible && titleSuggestionsChipItems.isNotEmpty(),
            ) {
                MyHorizontalScrollingSelectionGroup(
                    items = titleSuggestionsChipItems,
                    onSelectionChange = { index ->
                        clearFocus()
                        data.updateTitle(TextFieldValue(data.titleSuggestions[index]))
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
                MyHorizontalScrollingRadioGroup(
                    items = transactionForValuesChipItems,
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
                    textFieldValue = data.uiState.description,
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_description,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_clear_description,
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
                        R.string.screen_add_or_edit_transaction_source_from
                    } else {
                        R.string.screen_add_or_edit_transaction_source
                    },
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_FROM
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
                        R.string.screen_add_or_edit_transaction_source_to
                    } else {
                        R.string.screen_add_or_edit_transaction_source
                    },
                    onClick = {
                        clearFocus()
                        addOrEditTransactionBottomSheetType =
                            AddOrEditTransactionBottomSheetType.SELECT_SOURCE_TO
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
                value = data.uiState.transactionDate.formattedDate(),
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_date,
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
                value = data.uiState.transactionTime.formattedTime(),
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_transaction_time,
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
                textStringResourceId = data.ctaButtonLabelTextStringResourceId,
                isEnabled = data.isCtaButtonEnabled,
                onClick = {
                    clearFocus()
                    if (data.uriData.isNotNullOrBlank()) {
                        data.uiState.amount.text.toDoubleOrNull()?.let {
                            startUpiPayment(it, data.uriData)
                        }
                    } else {
                        data.onCtaButtonClick()
                    }
                },
            )
        }
    }
}
