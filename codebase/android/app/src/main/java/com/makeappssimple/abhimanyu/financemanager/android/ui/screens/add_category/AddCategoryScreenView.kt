package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyExtendedFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationArrowBackIcon
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank

data class AddCategoryScreenViewData(
    val screenViewModel: AddCategoryViewModel,
)

@Composable
fun AddCategoryScreenView(
    data: AddCategoryScreenViewData,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_category_appbar_title,
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
                    value = data.screenViewModel.title,
                    label = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_category_title,
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
                    value = data.screenViewModel.description,
                    label = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_add_category_description,
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
                        keyboardType = KeyboardType.Number,
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
                        fontWeight = FontWeight.Bold,
                        color = if (
                            data.screenViewModel.title.isNotNullOrBlank()
                        ) {
                            White
                        } else {
                            DarkGray
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
