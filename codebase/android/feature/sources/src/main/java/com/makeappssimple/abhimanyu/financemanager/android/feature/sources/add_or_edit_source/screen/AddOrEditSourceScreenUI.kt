package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextFieldEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

enum class AddOrEditSourceBottomSheetType : BottomSheetType {
    NONE,
}

enum class AddOrEditSourceScreenUIErrorText(
    @StringRes val textStringResourceId: Int,
) {
    SOURCE_EXISTS(
        textStringResourceId = R.string.screen_add_or_edit_source_error_source_exists,
    ),
}

@Immutable
data class AddOrEditSourceScreenUIVisibilityData(
    val balanceAmountTextField: Boolean = false,
    val nameTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
    val sourceTypesRadioGroup: Boolean = false,
)

@Immutable
data class AddOrEditSourceScreenUIErrorData(
    val balanceAmountTextField: AddOrEditSourceScreenUIErrorText? = null,
    val nameTextField: AddOrEditSourceScreenUIErrorText? = null,
)

@Immutable
data class AddOrEditSourceScreenUIData(
    val errorData: AddOrEditSourceScreenUIErrorData = AddOrEditSourceScreenUIErrorData(),
    val isValidSourceData: Boolean = false,
    val sourceIsNotCash: Boolean = false,
    val selectedSourceTypeIndex: Int = 0,
    val sourceTypes: List<SourceType> = emptyList(),
    val balanceAmountValue: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
)

@Immutable
internal data class AddOrEditSourceScreenUIEvents(
    val clearBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedSourceTypeIndex: (updatedIndex: Int) -> Unit,
)

@Composable
internal fun AddOrEditSourceScreenUI(
    events: AddOrEditSourceScreenUIEvents,
    uiState: AddOrEditSourceScreenUIState,
    state: CommonScreenUIState,
) {
    LaunchedEffect(
        key1 = uiState.visibilityData.balanceAmountTextField,
        key2 = uiState.visibilityData.nameTextField,
    ) {
        /*
        TODO(Abhi): Fix focus requester
        if (isBalanceAmountTextFieldVisible) {
            balanceAmountTextFieldFocusRequester.requestFocus()
        } else if (isNameTextFieldVisible) {
            nameTextFieldFocusRequester.requestFocus()
        }
        */
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.addOrEditSourceBottomSheetType != AddOrEditSourceBottomSheetType.NONE,
        bottomSheetType = uiState.addOrEditSourceBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.addOrEditSourceBottomSheetType) {
                AddOrEditSourceBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = uiState.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.addOrEditSourceBottomSheetType != AddOrEditSourceBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
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
            if (uiState.visibilityData.sourceTypesRadioGroup) {
                MyRadioGroup(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                    data = MyRadioGroupData(
                        items = uiState.sourceTypesChipUIDataList,
                        selectedItemIndex = uiState.selectedSourceTypeIndex,
                    ),
                    events = MyRadioGroupEvents(
                        onSelectionChange = events.updateSelectedSourceTypeIndex,
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
                        isError = uiState.visibilityData.nameTextFieldErrorText,
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
                    textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                ),
                events = SaveButtonEvents(
                    onClick = events.onCtaButtonClick,
                ),
            )
        }
    }
}
