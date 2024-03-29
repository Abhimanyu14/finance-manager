package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.annotation.StringRes
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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import kotlinx.coroutines.delay

enum class AddOrEditTransactionForScreenUIError(
    @StringRes val textStringResourceId: Int,
) {
    EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_transaction_for_error_exists,
    ),
}

@Composable
internal fun AddOrEditTransactionForScreenUI(
    uiState: AddOrEditTransactionForScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AddOrEditTransactionForScreenUIEvent) -> Unit = {},
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
        showModalBottomSheet = uiState.screenBottomSheetType != AddOrEditTransactionForScreenBottomSheetType.NONE,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION_FOR,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                AddOrEditTransactionForScreenBottomSheetType.DELETE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForScreenBottomSheetType.EDIT -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForScreenBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = {
                    handleUIEvents(AddOrEditTransactionForScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != AddOrEditTransactionForScreenBottomSheetType.NONE,
        backHandlerEnabled = uiState.screenBottomSheetType != AddOrEditTransactionForScreenBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
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
                    supportingText = if (uiState.titleTextFieldErrorTextStringResourceId.isNotNull()) {
                        {
                            MyText(
                                text = stringResource(
                                    id = uiState.titleTextFieldErrorTextStringResourceId,
                                ),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.error,
                                ),
                            )
                        }
                    } else {
                        null
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
                ),
                events = MyOutlinedTextFieldEvents(
                    onClickTrailingIcon = {
                        handleUIEvents(AddOrEditTransactionForScreenUIEvent.ClearTitle)
                    },
                    onValueChange = { updatedTitle ->
                        handleUIEvents(
                            AddOrEditTransactionForScreenUIEvent.UpdateTitle(
                                updatedTitle = updatedTitle,
                            )
                        )
                    },
                ),
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
                    isEnabled = uiState.isCtaButtonEnabled.orFalse(),
                    isLoading = uiState.isLoading,
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                events = SaveButtonEvents(
                    onClick = {
                        handleUIEvents(AddOrEditTransactionForScreenUIEvent.OnCtaButtonClick)
                    },
                ),
            )
        }
    }
}
