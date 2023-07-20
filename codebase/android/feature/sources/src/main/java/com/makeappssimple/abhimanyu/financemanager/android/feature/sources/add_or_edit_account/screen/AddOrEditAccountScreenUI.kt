package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.screen

import androidx.annotation.StringRes
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

enum class AddOrEditAccountBottomSheetType : BottomSheetType {
    NONE,
}

enum class AddOrEditAccountScreenUIErrorText(
    @StringRes val textStringResourceId: Int,
) {
    ACCOUNT_EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_source_error_source_exists,
    ),
}

@Immutable
data class AddOrEditAccountScreenUIVisibilityData(
    val balanceAmountTextField: Boolean = false,
    val nameTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
    val accountTypesRadioGroup: Boolean = false,
)

@Immutable
data class AddOrEditAccountScreenUIErrorData(
    val balanceAmountTextField: AddOrEditAccountScreenUIErrorText? = null,
    val nameTextField: AddOrEditAccountScreenUIErrorText? = null,
)

@Immutable
data class AddOrEditAccountScreenUIData(
    val errorData: AddOrEditAccountScreenUIErrorData = AddOrEditAccountScreenUIErrorData(),
    val isValidAccountData: Boolean = false,
    val accountIsNotCash: Boolean = false,
    val selectedAccountTypeIndex: Int = 0,
    val accountTypes: List<SourceType> = emptyList(),
    val balanceAmountValue: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
)

@Immutable
internal data class AddOrEditAccountScreenUIEvents(
    val clearBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedAccountTypeIndex: (updatedIndex: Int) -> Unit,
)

@Composable
internal fun AddOrEditAccountScreenUI(
    events: AddOrEditAccountScreenUIEvents,
    uiState: AddOrEditAccountScreenUIState,
    state: CommonScreenUIState,
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
            .fillMaxSize(),
        sheetContent = {
            when (uiState.addOrEditAccountBottomSheetType) {
                AddOrEditAccountBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                    events = MyRadioGroupEvents(
                        onSelectionChange = events.updateSelectedAccountTypeIndex,
                    ),
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
                        labelTextStringResourceId = R.string.screen_add_or_edit_source_name,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_source_clear_name,
                        supportingText = if (uiState.visibilityData.nameTextFieldErrorText) {
                            {
                                AnimatedVisibility(
                                    visible = uiState.visibilityData.nameTextFieldErrorText,
                                ) {
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
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                    ),
                    events = MyOutlinedTextFieldEvents(
                        onClickTrailingIcon = events.clearName,
                        onValueChange = events.updateName,
                    ),
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
                        labelTextStringResourceId = R.string.screen_add_or_edit_source_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_source_clear_balance_amount_value,
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
                    events = MyOutlinedTextFieldEvents(
                        onClickTrailingIcon = events.clearBalanceAmountValue,
                        onValueChange = events.updateBalanceAmountValue,
                    ),
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
                events = SaveButtonEvents(
                    onClick = events.onCtaButtonClick,
                ),
            )
        }
    }
}