package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield.MyOutlinedTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event.AddTransactionForScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.stringResourceId
import kotlinx.coroutines.delay

@Composable
internal fun AddTransactionForScreenUI(
    uiState: AddTransactionForScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AddTransactionForScreenUIEvent) -> Unit = {},
) {
    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            delay(300) // Source - https://stackoverflow.com/a/72783456/9636037
            state.focusRequester.requestFocus()
        }
    }

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION_FOR,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AddTransactionForScreenBottomSheetType.Delete -> {
                    VerticalSpacer()
                }

                is AddTransactionForScreenBottomSheetType.Edit -> {
                    VerticalSpacer()
                }

                is AddTransactionForScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
                navigationAction = {
                    handleUIEvent(AddTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AddTransactionForScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            MyOutlinedTextField(
                data = MyOutlinedTextFieldData(
                    isLoading = uiState.isLoading,
                    textFieldValue = uiState.title.orEmpty(),
                    labelTextStringResourceId = R.string.screen_add_or_edit_transaction_for_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_for_clear_title,
                    supportingText = {
                        AnimatedVisibility(
                            visible = uiState.titleError != AddTransactionForScreenTitleError.None,
                        ) {
                            uiState.titleError.stringResourceId?.let { titleTextFieldErrorTextStringResourceId ->
                                MyText(
                                    text = stringResource(
                                        id = titleTextFieldErrorTextStringResourceId,
                                    ),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.error,
                                    ),
                                )
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            state.focusManager.clearFocus()
                            handleUIEvent(AddTransactionForScreenUIEvent.OnCtaButtonClick)
                        },
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                            handleUIEvent(AddTransactionForScreenUIEvent.OnClearTitleButtonClick)
                        }

                        is MyOutlinedTextFieldEvent.OnValueChange -> {
                            handleUIEvent(
                                AddTransactionForScreenUIEvent.OnTitleUpdated(
                                    updatedTitle = event.updatedValue,
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        all = 16.dp,
                    ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(AddTransactionForScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
        }
    }
}
