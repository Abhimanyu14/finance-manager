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
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

private enum class AddOrEditTransactionForBottomSheetType : BottomSheetType {
    DELETE,
    EDIT,
    NONE,
}

@Immutable
internal data class AddOrEditTransactionForScreenUIData(
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val title: TextFieldValue,
)

@Immutable
internal data class AddOrEditTransactionForScreenUIEvents(
    val clearTitle: () -> Unit,
    val isValidTitle: () -> Boolean,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit,
)

@Composable
internal fun AddOrEditTransactionForScreenUI(
    data: AddOrEditTransactionForScreenUIData,
    events: AddOrEditTransactionForScreenUIEvents,
    state: CommonScreenUIState,
) {
    var addOrEditTransactionForBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditTransactionForBottomSheetType.NONE,
        )
    }
    val resetBottomSheetType = {
        addOrEditTransactionForBottomSheetType = AddOrEditTransactionForBottomSheetType.NONE
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    BottomSheetHandler(
        showModalBottomSheet = addOrEditTransactionForBottomSheetType != AddOrEditTransactionForBottomSheetType.NONE,
        bottomSheetType = addOrEditTransactionForBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (addOrEditTransactionForBottomSheetType) {
                AddOrEditTransactionForBottomSheetType.DELETE -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForBottomSheetType.EDIT -> {
                    VerticalSpacer()
                }

                AddOrEditTransactionForBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = data.appBarTitleTextStringResourceId,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = addOrEditTransactionForBottomSheetType != AddOrEditTransactionForBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = resetBottomSheetType,
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
                textFieldValue = data.title,
                labelTextStringResourceId = R.string.screen_add_or_edit_transaction_for_title,
                trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_transaction_for_clear_title,
                onClickTrailingIcon = {
                    events.clearTitle()
                },
                onValueChange = { updatedTitle ->
                    events.updateTitle(updatedTitle)
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
                isEnabled = events.isValidTitle(),
                onClick = events.onCtaButtonClick,
            )
        }
    }
}
