package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyExtendedFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationArrowBackIcon
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ReadonlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.dayOfMonth
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.month
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.setDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.year
import java.util.*

data class AddTransactionScreenViewData(
    val screenViewModel: AddTransactionViewModel,
)

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun AddTransactionScreenView(
    data: AddTransactionScreenViewData,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }

    val categories by data.screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        data.screenViewModel.transactionCalendar =
            (data.screenViewModel.transactionCalendar.clone() as Calendar)
                .setDate(
                    dayOfMonth = dayOfMonth,
                    month = month,
                    year = year,
                )
    }
    val transactionDatePickerDialog = DatePickerDialog(
        context,
        onDateSetListener,
        data.screenViewModel.transactionCalendar.year,
        data.screenViewModel.transactionCalendar.month,
        data.screenViewModel.transactionCalendar.dayOfMonth,
    )

    LaunchedEffect(
        key1 = categories,
    ) {
        data.screenViewModel.category = categories.firstOrNull {
            it.title.contains("Default")
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            AddTransactionSelectCategoryBottomSheet(
                data = AddTransactionSelectCategoryBottomSheetData(
                    list = categories.map { category ->
                        AddTransactionSelectCategoryBottomSheetItemData(
                            text = category.title,
                            onClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = coroutineScope,
                                    modalBottomSheetState = modalBottomSheetState,
                                ) {
                                    data.screenViewModel.category = category
                                }
                            },
                        )
                    }.toList(),
                ),
            )
        },
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_transaction_appbar_title,
                                ),
                                color = Primary,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigateUp(
                                    navigationManager = data.screenViewModel.navigationManager,
                                )
                            },
                        ) {
                            NavigationArrowBackIcon()
                        }
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
            Box(
                modifier = Modifier
                    .background(
                        color = Surface,
                    )
                    .fillMaxSize()
                    .padding(
                        paddingValues = innerPadding,
                    ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            state = rememberScrollState(),
                        ),
                ) {
                    OutlinedTextField(
                        value = data.screenViewModel.amount,
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_transaction_amount,
                                ),
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
                                focusManager.moveFocus(
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
                                focusRequester = focusRequester,
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                    OutlinedTextField(
                        value = data.screenViewModel.title,
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_transaction_title,
                                ),
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
                            onNext = {
                                focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                                    id = R.string.screen_add_transaction_description,
                                ),
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
                    MyRadioGroup(
                        items = TransactionFor.values().map { transactionFor ->
                            MyRadioGroupItem(
                                text = transactionFor.title,
                            )
                        },
                        selectedItemIndex = data.screenViewModel.transactionFor.ordinal,
                        onSelectionChange = { ordinal ->
                            data.screenViewModel.transactionFor =
                                TransactionFor.values().getOrElse(ordinal) { TransactionFor.SELF }
                        },
                        modifier = Modifier
                            .padding(
                                all = 12.dp,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 0.dp,
                            ),
                    )
                    MyRadioGroup(
                        items = TransactionType.values().map { transactionType ->
                            MyRadioGroupItem(
                                text = transactionType.title,
                            )
                        },
                        selectedItemIndex = data.screenViewModel.transactionType.ordinal,
                        onSelectionChange = { ordinal ->
                            data.screenViewModel.transactionType =
                                TransactionType.values()
                                    .getOrElse(ordinal) { TransactionType.EXPENSE }
                        },
                        modifier = Modifier
                            .padding(
                                all = 12.dp,
                            ),
                    )
                    ReadonlyTextField(
                        value = data.screenViewModel.transactionDateTextFieldValue,
                        onValueChange = {},
                        onClick = {
                            transactionDatePickerDialog.show()
                        },
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_transaction_transaction_date,
                                ),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                            ),
                    )
                    ReadonlyTextField(
                        value = data.screenViewModel.categoryTextFieldValue,
                        onValueChange = {},
                        onClick = {
                            toggleModalBottomSheetState(
                                coroutineScope = coroutineScope,
                                modalBottomSheetState = modalBottomSheetState,
                            ) {}
                        },
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.screen_add_transaction_category,
                                ),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                all = 16.dp,
                            ),
                    )
                    MyExtendedFloatingActionButton(
                        onClickLabel = stringResource(
                            id = R.string.screen_add_transaction_floating_action_button_content_description,
                        ),
                        enabled = data.screenViewModel.amount.isNotNullOrBlank() &&
                                data.screenViewModel.title.isNotNullOrBlank(),
                        onClick = {
                            data.screenViewModel.insertTransaction()
                        },
                        modifier = Modifier
                            .padding(
                                all = 16.dp,
                            ),
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_transaction_floating_action_button_content_description,
                            ),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = if (
                                data.screenViewModel.amount.isNotNullOrBlank() &&
                                data.screenViewModel.title.isNotNullOrBlank()
                            ) {
                                Color.White
                            } else {
                                Color.DarkGray
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
