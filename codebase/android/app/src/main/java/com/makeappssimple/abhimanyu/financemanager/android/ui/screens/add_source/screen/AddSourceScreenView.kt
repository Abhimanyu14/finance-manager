package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.screen

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
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields.MyOutlinedTextField

enum class AddSourceBottomSheetType : BottomSheetType {
    NONE,
}

data class AddSourceScreenViewData(
    val selectedSourceTypeIndex: Int,
    val sourceTypes: List<SourceType>,
    val navigationManager: NavigationManager,
    val name: String,
    val clearName: () -> Unit,
    val insertSource: () -> Unit,
    val isValidSourceData: () -> Boolean,
    val updateName: (updatedName: String) -> Unit,
    val updateSelectedSourceTypeIndex: (updatedIndex: Int) -> Unit,
)

@Composable
fun AddSourceScreenView(
    data: AddSourceScreenViewData,
    state: CommonScreenViewState,
) {
    var addSourceBottomSheetType by remember {
        mutableStateOf(
            value = AddSourceBottomSheetType.NONE,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                addSourceBottomSheetType = AddSourceBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = addSourceBottomSheetType != AddSourceBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        addSourceBottomSheetType = AddSourceBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addSourceBottomSheetType) {
                AddSourceBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.navigationManager,
                    titleTextStringResourceId = R.string.screen_add_source_appbar_title,
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
                    MyRadioGroup(
                        items = data.sourceTypes
                            .map { sourceType ->
                                MyRadioGroupItem(
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
                        labelTextStringResourceId = R.string.screen_add_source_name,
                        trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_source_clear_name,
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
                    SaveButton(
                        textStringResourceId = R.string.screen_add_source_floating_action_button_content_description,
                        isEnabled = data.isValidSourceData(),
                        onClick = {
                            data.insertSource()
                        },
                    )
                }
            }
        }
    }
}
