package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.OutlinedTextFieldLabelText
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

data class AddSourceScreenViewData(
    val screenViewModel: AddSourceViewModel,
)

@Composable
fun AddSourceScreenView(
    data: AddSourceScreenViewData,
    state: AddSourceScreenViewState,
) {
    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    // TODO-Abhi: Make 'cash' keyword restricted
    Scaffold(
        scaffoldState = state.scaffoldState,
        topBar = {
            MyTopAppBar(
                navigationManager = data.screenViewModel.navigationManager,
                titleText = stringResource(
                    id = R.string.screen_add_source_appbar_title,
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
                    items = data.screenViewModel.sourceTypes
                        .map { sourceType ->
                            MyRadioGroupItem(
                                text = sourceType.title,
                                iconKey = sourceType.title,
                            )
                        },
                    selectedItemIndex = data.screenViewModel.selectedSourceTypeIndex,
                    onSelectionChange = { index ->
                        data.screenViewModel.selectedSourceTypeIndex = index
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
                OutlinedTextField(
                    value = data.screenViewModel.name,
                    label = {
                        OutlinedTextFieldLabelText(
                            textStringResourceId = R.string.screen_add_source_name,
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = data.screenViewModel.name.isNotNullOrBlank(),
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            MyIconButton(
                                onClickLabel = stringResource(
                                    id = R.string.screen_add_source_clear_name,
                                ),
                                onClick = {
                                    data.screenViewModel.name = ""
                                },
                                modifier = Modifier
                                    .padding(
                                        end = 4.dp,
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = stringResource(
                                        id = R.string.screen_add_source_clear_name,
                                    ),
                                )
                            }
                        }
                    },
                    onValueChange = {
                        data.screenViewModel.name = it
                    },
                    keyboardActions = KeyboardActions(
                        onNext = {
                            state.focusManager.moveFocus(
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
                        .focusRequester(
                            focusRequester = state.focusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
                OutlinedTextField(
                    value = data.screenViewModel.balanceAmount,
                    label = {
                        OutlinedTextFieldLabelText(
                            textStringResourceId = R.string.screen_add_source_balance_amount,
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = data.screenViewModel.balanceAmount.isNotNullOrBlank(),
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            MyIconButton(
                                onClickLabel = stringResource(
                                    id = R.string.screen_add_source_clear_balance_amount,
                                ),
                                onClick = {
                                    data.screenViewModel.balanceAmount = ""
                                },
                                modifier = Modifier
                                    .padding(
                                        end = 4.dp,
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = stringResource(
                                        id = R.string.screen_add_source_clear_balance_amount,
                                    ),
                                )
                            }
                        }
                    },
                    onValueChange = {
                        data.screenViewModel.balanceAmount = it
                    },
                    keyboardActions = KeyboardActions(
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
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
                SaveButton(
                    textStringResourceId = R.string.screen_add_source_floating_action_button_content_description,
                    isEnabled = data.screenViewModel.isValidSourceData(),
                    onClick = {
                        data.screenViewModel.insertSource()
                    },
                )
            }
        }
    }
}
