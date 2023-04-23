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
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

internal enum class AddOrEditSourceBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
internal data class AddOrEditSourceScreenViewData(
    val isBalanceAmountTextFieldVisible: Boolean,
    val isNameTextFieldVisible: Boolean,
    val isSourceTypesRadioGroupVisible: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val selectedSourceTypeIndex: Int,
    val sourceTypes: List<SourceType>,
    val navigationManager: NavigationManager,
    val balanceAmountValue: TextFieldValue,
    val name: TextFieldValue,
    val clearBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val isValidSourceData: () -> Boolean,
    val onCtaButtonClick: () -> Unit,
    val updateBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedSourceTypeIndex: (updatedIndex: Int) -> Unit,
)

@Composable
internal fun AddOrEditSourceScreenView(
    data: AddOrEditSourceScreenViewData,
    state: CommonScreenViewState,
) {
    var addOrEditSourceBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditSourceBottomSheetType.NONE,
        )
    }
    val nameTextFieldModifier = if (data.isBalanceAmountTextFieldVisible) {
        Modifier
    } else {
        Modifier.focusRequester(
            focusRequester = state.focusRequester,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                addOrEditSourceBottomSheetType = AddOrEditSourceBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addOrEditSourceBottomSheetType) {
                AddOrEditSourceBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = {
                    data.navigationManager.navigate(
                        navigationCommand = MyNavigationDirections.NavigateUp
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = addOrEditSourceBottomSheetType != AddOrEditSourceBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            addOrEditSourceBottomSheetType = AddOrEditSourceBottomSheetType.NONE
        },
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
            AnimatedVisibility(
                visible = data.isSourceTypesRadioGroupVisible,
            ) {
                MyRadioGroup(
                    items = data.sourceTypes
                        .map { sourceType ->
                            ChipItem(
                                text = sourceType.title,
                                icon = sourceType.icon,
                            )
                        },
                    selectedItemIndex = data.selectedSourceTypeIndex,
                    onSelectionChange = { updatedIndex ->
                        data.updateSelectedSourceTypeIndex(updatedIndex)
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = data.isNameTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = data.name,
                    labelTextStringResourceId = R.string.screen_add_or_edit_source_name,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_source_clear_name,
                    onClickTrailingIcon = {
                        data.clearName()
                    },
                    onValueChange = { updatedName ->
                        data.updateName(updatedName)
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
                    modifier = nameTextFieldModifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            AnimatedVisibility(
                visible = data.isBalanceAmountTextFieldVisible,
            ) {
                MyOutlinedTextField(
                    textFieldValue = data.balanceAmountValue,
                    labelTextStringResourceId = R.string.screen_edit_source_balance_amount_value,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_source_clear_balance_amount_value,
                    onClickTrailingIcon = {
                        data.clearBalanceAmountValue()
                    },
                    onValueChange = { updatedBalanceAmountValue ->
                        data.updateBalanceAmountValue(updatedBalanceAmountValue)
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
                            focusRequester = state.focusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            SaveButton(
                textStringResourceId = data.ctaButtonLabelTextStringResourceId,
                isEnabled = data.isValidSourceData(),
                onClick = {
                    data.onCtaButtonClick()
                },
            )
        }
    }
}
