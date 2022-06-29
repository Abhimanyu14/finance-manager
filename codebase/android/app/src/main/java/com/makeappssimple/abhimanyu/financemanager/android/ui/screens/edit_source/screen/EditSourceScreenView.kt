package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.screen

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
import androidx.compose.runtime.collectAsState
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
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.viewmodel.EditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape

enum class EditSourceBottomSheetType : BottomSheetType {
    NONE,
}

data class EditSourceScreenViewData(
    val screenViewModel: EditSourceScreenViewModel,
    val sourceId: Int?,
)

@Composable
fun EditSourceScreenView(
    data: EditSourceScreenViewData,
    state: EditSourceScreenViewState,
) {
    val selectedSourceTypeIndex by data.screenViewModel.selectedSourceTypeIndex.collectAsState()
    val source by data.screenViewModel.source.collectAsState(
        initial = null,
    )
    val name by data.screenViewModel.name.collectAsState()
    val balanceAmountValue by data.screenViewModel.balanceAmountValue.collectAsState()
    var sourcesBottomSheetType by remember {
        mutableStateOf(
            value = EditSourceBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                sourcesBottomSheetType = EditSourceBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = sourcesBottomSheetType != EditSourceBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        sourcesBottomSheetType = EditSourceBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (sourcesBottomSheetType) {
                EditSourceBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleTextStringResourceId = R.string.screen_edit_source_appbar_title,
                    isNavigationIconVisible = true,
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
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
                    if (source?.type != SourceType.CASH) {
                        MyRadioGroup(
                            items = data.screenViewModel.sourceTypes
                                .map { sourceType ->
                                    MyRadioGroupItem(
                                        text = sourceType.title,
                                        iconKey = sourceType.title,
                                    )
                                },
                            selectedItemIndex = selectedSourceTypeIndex,
                            onSelectionChange = { index ->
                                data.screenViewModel.updateSelectedSourceTypeIndex(
                                    updatedIndex = index,
                                )
                            },
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp,
                                ),
                        )
                        MyOutlinedTextField(
                            value = name,
                            labelTextStringResourceId = R.string.screen_edit_source_name,
                            trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_source_clear_name,
                            onClickTrailingIcon = {
                                data.screenViewModel.clearName()
                            },
                            onValueChange = {
                                data.screenViewModel.updateName(
                                    updatedName = it,
                                )
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
                        value = balanceAmountValue,
                        labelTextStringResourceId = R.string.screen_edit_source_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_edit_source_clear_balance_amount_value,
                        onClickTrailingIcon = {
                            data.screenViewModel.clearBalanceAmountValue()
                        },
                        onValueChange = {
                            data.screenViewModel.updateBalanceAmountValue(
                                updatedBalanceAmountValue = it,
                            )
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
                        isEnabled = data.screenViewModel.isValidSourceData(),
                        onClick = {
                            data.screenViewModel.updateSource()
                        },
                    )
                }
            }
        }
    }
}
