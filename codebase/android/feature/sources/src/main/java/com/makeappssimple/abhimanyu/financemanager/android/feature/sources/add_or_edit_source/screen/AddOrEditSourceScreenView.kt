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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

private enum class AddOrEditSourceBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class AddOrEditSourceScreenViewVisibilityData(
    val balanceAmount: Boolean,
    val name: Boolean,
    val sourceTypes: Boolean,
)

@Immutable
data class AddOrEditSourceScreenViewErrorData(
    val balanceAmount: String? = null,
    val name: String? = null,
)

@Immutable
internal data class AddOrEditSourceScreenViewData(
    val visibilityData: AddOrEditSourceScreenViewVisibilityData,
    val errorData: AddOrEditSourceScreenViewErrorData,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    val selectedSourceTypeIndex: Int,
    val sourceTypes: List<SourceType>,
    val balanceAmountValue: TextFieldValue,
    val name: TextFieldValue,
)

@Immutable
internal data class AddOrEditSourceScreenViewEvents(
    val clearBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val isValidSourceData: () -> Boolean,
    val navigateUp: () -> Unit,
    val onCtaButtonClick: () -> Unit,
    val updateBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedSourceTypeIndex: (updatedIndex: Int) -> Unit,
)

@Composable
internal fun AddOrEditSourceScreenView(
    data: AddOrEditSourceScreenViewData,
    events: AddOrEditSourceScreenViewEvents,
    state: CommonScreenViewState,
) {
    var addOrEditSourceBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditSourceBottomSheetType.NONE,
        )
    }
    val resetBottomSheetType = {
        addOrEditSourceBottomSheetType = AddOrEditSourceBottomSheetType.NONE
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        state.focusRequester.requestFocus()
    }

    BottomSheetHandler(
        showModalBottomSheet = addOrEditSourceBottomSheetType != AddOrEditSourceBottomSheetType.NONE,
        bottomSheetType = addOrEditSourceBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

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
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = addOrEditSourceBottomSheetType != AddOrEditSourceBottomSheetType.NONE,
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
            if (data.visibilityData.sourceTypes) {
                MyRadioGroup(
                    items = data.sourceTypes
                        .map { sourceType ->
                            ChipUIData(
                                text = sourceType.title,
                                icon = sourceType.icon,
                            )
                        },
                    selectedItemIndex = data.selectedSourceTypeIndex,
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
            if (data.visibilityData.name) {
                MyOutlinedTextField(
                    textFieldValue = data.name,
                    labelTextStringResourceId = R.string.screen_add_or_edit_source_name,
                    trailingIconContentDescriptionTextStringResourceId = R.string.screen_add_or_edit_source_clear_name,
                    onClickTrailingIcon = events.clearName,
                    onValueChange = { updatedName ->
                        events.updateName(updatedName)
                    },
                    supportingText = {
                        AnimatedVisibility(
                            visible = data.errorData.name.isNotNullOrBlank(),
                        ) {
                            MyText(
                                text = data.errorData.name.orEmpty(),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.error,
                                ),
                            )
                        }
                    },
                    isError = data.errorData.name.isNotNullOrBlank(),
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
                        .then(
                            if (data.visibilityData.balanceAmount) {
                                Modifier
                            } else {
                                Modifier.focusRequester(
                                    focusRequester = state.focusRequester,
                                )
                            }
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                )
            }
            if (data.visibilityData.balanceAmount) {
                MyOutlinedTextField(
                    textFieldValue = data.balanceAmountValue,
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
                isEnabled = events.isValidSourceData(),
                onClick = events.onCtaButtonClick,
            )
        }
    }
}
