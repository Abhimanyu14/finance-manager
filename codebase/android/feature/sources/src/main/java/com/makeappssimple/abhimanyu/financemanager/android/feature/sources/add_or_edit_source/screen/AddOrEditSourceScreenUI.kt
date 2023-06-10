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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

enum class AddOrEditSourceBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class AddOrEditSourceScreenUIVisibilityData(
    val balanceAmount: Boolean = false,
    val name: Boolean = false,
    val sourceTypes: Boolean = false,
)

@Immutable
data class AddOrEditSourceScreenUIErrorData(
    val balanceAmount: String? = null,
    val name: String? = null,
)

@Immutable
data class AddOrEditSourceScreenUIData(
    val errorData: AddOrEditSourceScreenUIErrorData = AddOrEditSourceScreenUIErrorData(),
    val visibilityData: AddOrEditSourceScreenUIVisibilityData = AddOrEditSourceScreenUIVisibilityData(),
    val isValidSourceData: Boolean = false,
    @StringRes val appBarTitleTextStringResourceId: Int = 0,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = 0,
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
        key1 = uiState.isNameTextFieldVisible,
        key2 = uiState.isBalanceAmountTextFieldVisible,
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
            if (uiState.isSourceTypesRadioGroupVisible) {
                MyRadioGroup(
                    items = uiState.sourceTypesChipUIDataList,
                    selectedItemIndex = uiState.selectedSourceTypeIndex,
                    onSelectionChange = { updatedIndex ->
                        events.updateSelectedSourceTypeIndex(updatedIndex)
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            if (uiState.isNameTextFieldVisible) {
                MyOutlinedTextField(
                    textFieldValue = uiState.name,
                    labelTextStringResourceId = R.string.screen_add_or_edit_source_name,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_source_clear_name,
                    onClickTrailingIcon = events.clearName,
                    onValueChange = { updatedName ->
                        events.updateName(updatedName)
                    },
                    supportingText = {
                        AnimatedVisibility(
                            visible = uiState.isNameTextFieldErrorTextVisible,
                        ) {
                            MyText(
                                text = uiState.nameTextFieldErrorText,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.error,
                                ),
                            )
                        }
                    },
                    isError = uiState.isNameTextFieldErrorTextVisible,
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
                    modifier = Modifier
                        .focusRequester(
                            focusRequester = uiState.nameTextFieldFocusRequester,
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            if (uiState.isBalanceAmountTextFieldVisible) {
                MyOutlinedTextField(
                    textFieldValue = uiState.balanceAmountValue,
                    labelTextStringResourceId = R.string.screen_edit_source_balance_amount_value,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_source_clear_balance_amount_value,
                    onClickTrailingIcon = events.clearBalanceAmountValue,
                    onValueChange = { updatedBalanceAmountValue ->
                        events.updateBalanceAmountValue(updatedBalanceAmountValue)
                    },
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = uiState.balanceAmountTextFieldFocusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            SaveButton(
                textStringResourceId = uiState.ctaButtonLabelTextStringResourceId,
                isEnabled = uiState.isCtaButtonEnabled,
                onClick = events.onCtaButtonClick,
            )
        }
    }
}
