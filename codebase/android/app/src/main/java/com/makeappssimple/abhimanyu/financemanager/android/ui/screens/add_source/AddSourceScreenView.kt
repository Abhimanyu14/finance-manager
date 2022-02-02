package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyExtendedFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

data class AddSourceScreenViewData(
    val screenViewModel: AddSourceViewModel,
)

@Composable
fun AddSourceScreenView(
    data: AddSourceScreenViewData,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }

    // TODO-Abhi: Make 'cash' keyword restricted
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.screen_add_source_appbar_title,
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
                MyRadioGroup(
                    items = data.screenViewModel.sourceTypes
                        .map { sourceType ->
                            MyRadioGroupItem(
                                text = sourceType.title,
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
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_source_name,
                            ),
                            color = Color.DarkGray,
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
                        .focusRequester(
                            focusRequester = focusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
                OutlinedTextField(
                    value = data.screenViewModel.balanceAmount,
                    label = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_source_balance_amount,
                            ),
                            color = Color.DarkGray,
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
                            focusManager.clearFocus()
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
                MyExtendedFloatingActionButton(
                    onClickLabel = stringResource(
                        id = R.string.screen_add_source_floating_action_button_content_description,
                    ),
                    enabled = data.screenViewModel.name.isNotNullOrBlank() &&
                            data.screenViewModel.balanceAmount.isNotNullOrBlank(),
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = Color.Transparent,
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (
                            data.screenViewModel.name.isNotNullOrBlank() &&
                            data.screenViewModel.balanceAmount.isNotNullOrBlank()
                        ) {
                            Color.Transparent
                        } else {
                            LightGray
                        },
                    ),
                    onClick = {
                        data.screenViewModel.insertSource()
                    },
                    modifier = Modifier
                        .padding(
                            all = 16.dp,
                        ),
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.screen_add_source_floating_action_button_content_description,
                        ),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = if (
                            data.screenViewModel.name.isNotNullOrBlank() &&
                            data.screenViewModel.balanceAmount.isNotNullOrBlank()
                        ) {
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
