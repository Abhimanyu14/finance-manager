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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

internal enum class AddOrEditTransactionForBottomSheetType : BottomSheetType {
    NONE,
    EDIT,
    DELETE,
}

@Immutable
internal data class AddOrEditTransactionForScreenViewData(
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val navigationManager: NavigationManager,
    val title: String,
    val clearTitle: () -> Unit,
    val isValidTitle: () -> Boolean,
    val onCtaButtonClick: () -> Unit,
    val updateTitle: (updatedTitle: String) -> Unit,
)

@Composable
internal fun AddOrEditTransactionForScreenView(
    data: AddOrEditTransactionForScreenViewData,
    state: CommonScreenViewState,
) {
    var addOrEditTransactionForBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionForBottomSheetType.NONE,
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
                addOrEditTransactionForBottomSheetType = AddOrEditTransactionForBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = addOrEditTransactionForBottomSheetType != AddOrEditTransactionForBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        addOrEditTransactionForBottomSheetType = AddOrEditTransactionForBottomSheetType.NONE
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addOrEditTransactionForBottomSheetType) {
                AddOrEditTransactionForBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForBottomSheetType.EDIT -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForBottomSheetType.DELETE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = {
                    navigateUp(
                        navigationManager = data.navigationManager,
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
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
            MyOutlinedTextField(
                value = data.title,
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_for_title,
                trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_for_clear_title,
                onClickTrailingIcon = {
                    data.clearTitle()
                },
                onValueChange = { updatedTitle ->
                    data.updateTitle(updatedTitle)
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
                textStringResourceId = data.ctaButtonLabelTextStringResourceId,
                isEnabled = data.isValidTitle(),
                onClick = {
                    data.onCtaButtonClick()
                },
            )
        }
    }
}
