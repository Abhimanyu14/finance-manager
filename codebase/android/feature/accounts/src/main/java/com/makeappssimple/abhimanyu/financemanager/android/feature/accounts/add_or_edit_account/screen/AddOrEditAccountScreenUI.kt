package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

public enum class AddOrEditAccountScreenUIError(
    @StringRes public val textStringResourceId: Int,
) {
    ACCOUNT_EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_account_error_account_exists,
    ),
}

@Immutable
public data class AddOrEditAccountScreenUIVisibilityData(
    val balanceAmountTextField: Boolean = false,
    val minimumBalanceAmountTextField: Boolean = false,
    val nameTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
    val accountTypesRadioGroup: Boolean = false,
)

@Immutable
public data class AddOrEditAccountScreenUIErrorData(
    val balanceAmountTextField: AddOrEditAccountScreenUIError? = null,
    val nameTextField: AddOrEditAccountScreenUIError? = null,
)

@Composable
internal fun AddOrEditAccountScreenUI(
    uiState: AddOrEditAccountScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AddOrEditAccountScreenUIEvent) -> Unit = {},
) {
    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = uiState.visibilityData.balanceAmountTextField,
            key2 = uiState.visibilityData.nameTextField,
        ) {
            if (uiState.visibilityData.balanceAmountTextField) {
                uiState.balanceAmountTextFieldFocusRequester.requestFocus()
            } else if (uiState.visibilityData.nameTextField) {
                uiState.nameTextFieldFocusRequester.requestFocus()
            }
            state.keyboardController?.show()
        }
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_ACCOUNT,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AddOrEditAccountScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = {
                    handleUIEvent(AddOrEditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(AddOrEditAccountScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            if (uiState.visibilityData.accountTypesRadioGroup) {
                MyRadioGroup(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                    data = MyRadioGroupData(
                        isLoading = uiState.isLoading,
                        items = uiState.accountTypesChipUIDataList,
                        selectedItemIndex = uiState.selectedAccountTypeIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyRadioGroupEvent.OnSelectionChange -> {
                                handleUIEvent(
                                    AddOrEditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated(
                                        updatedIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.nameTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .focusRequester(
                            focusRequester = uiState.nameTextFieldFocusRequester,
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isError = uiState.visibilityData.nameTextFieldErrorText,
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.name,
                        labelTextStringResourceId = R.string.screen_add_or_edit_account_name,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_account_clear_name,
                        supportingText = if (uiState.visibilityData.nameTextFieldErrorText) {
                            {
                                MyText(
                                    text = uiState.nameTextFieldErrorTextStringResourceId?.run {
                                        stringResource(
                                            id = uiState.nameTextFieldErrorTextStringResourceId,
                                        )
                                    }.orEmpty(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.error,
                                    ),
                                )
                            }
                        } else {
                            null
                        },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                state.focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = if (uiState.visibilityData.balanceAmountTextField) {
                                ImeAction.Next
                            } else {
                                ImeAction.Done
                            },
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(AddOrEditAccountScreenUIEvent.OnClearNameButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    AddOrEditAccountScreenUIEvent.OnNameUpdated(
                                        updatedName = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.balanceAmountTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = uiState.balanceAmountTextFieldFocusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.balanceAmountValue,
                        labelTextStringResourceId = R.string.screen_add_or_edit_account_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_account_clear_balance_amount_value,
                        visualTransformation = AmountCommaVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                state.focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(AddOrEditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    AddOrEditAccountScreenUIEvent.OnBalanceAmountValueUpdated(
                                        updatedBalanceAmountValue = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.minimumBalanceAmountTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.minimumBalanceAmountValue,
                        labelTextStringResourceId = R.string.screen_add_or_edit_account_minimum_account_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_account_clear_minimum_account_balance_amount_value,
                        visualTransformation = AmountCommaVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(AddOrEditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    AddOrEditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated(
                                        updatedMinimumAccountBalanceAmountValue = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(AddOrEditAccountScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
        }
    }
}
