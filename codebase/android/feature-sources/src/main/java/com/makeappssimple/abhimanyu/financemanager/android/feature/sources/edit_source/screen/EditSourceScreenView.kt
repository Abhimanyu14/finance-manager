package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

internal enum class EditSourceBottomSheetType : BottomSheetType {
    NONE,
}

internal data class EditSourceScreenViewData(
    val selectedSourceTypeIndex: Int,
    val sourceId: Int?,
    val sourceTypes: List<SourceType>,
    val navigationManager: NavigationManager,
    val source: Source?,
    val balanceAmountValue: String,
    val name: String,
    val clearBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val deleteSource: (id: Int) -> Unit,
    val insertSource: () -> Unit,
    val isValidSourceData: () -> Boolean,
    val updateBalanceAmountValue: (updatedBalanceAmountValue: String) -> Unit,
    val updateName: (updatedName: String) -> Unit,
    val updateSelectedSourceTypeIndex: (updatedIndex: Int) -> Unit,
    val updateSource: () -> Unit,
)

@Composable
internal fun EditSourceScreenView(
    data: EditSourceScreenViewData,
    state: CommonScreenViewState,
) {
    var editSourceBottomSheetType by remember {
        mutableStateOf(
            value = EditSourceBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                editSourceBottomSheetType = EditSourceBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = editSourceBottomSheetType != EditSourceBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        editSourceBottomSheetType = EditSourceBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (editSourceBottomSheetType) {
                EditSourceBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_edit_source_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            MyScaffoldContentWrapper(
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
                    if (data.source?.type != SourceType.CASH) {
                        MyRadioGroup(
                            items = data.sourceTypes
                                .map { sourceType ->
                                    ChipItem(
                                        text = sourceType.title,
                                        iconKey = sourceType.title,
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
                        MyOutlinedTextField(
                            value = data.name,
                            labelTextStringResourceId = R.string.screen_edit_source_name,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_source_clear_name,
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
                    MyOutlinedTextField(
                        value = data.balanceAmountValue,
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
                    SaveButton(
                        textStringResourceId = R.string.screen_edit_source_floating_action_button_content_description,
                        isEnabled = data.isValidSourceData(),
                        onClick = {
                            data.updateSource()
                        },
                    )
                }
            }
        }
    }
}