package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemEvents

internal enum class AnalysisBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
internal data class AnalysisScreenViewData(
    val selectedTransactionTypeIndex: Int?,
    val transactionDataMappedByCategory: List<AnalysisListItemData>,
    val transactionTypesChipItems: List<ChipItem>,
)

@Immutable
internal data class AnalysisScreenViewEvents(
    val navigateUp: () -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
)

@Composable
internal fun AnalysisScreenView(
    data: AnalysisScreenViewData,
    events: AnalysisScreenViewEvents,
    state: CommonScreenViewState,
) {
    var settingsBottomSheetType by remember {
        mutableStateOf(
            value = AnalysisBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                settingsBottomSheetType = AnalysisBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (settingsBottomSheetType) {
                AnalysisBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_analysis_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = settingsBottomSheetType != AnalysisBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            settingsBottomSheetType = AnalysisBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            MyRadioGroup(
                items = data.transactionTypesChipItems,
                selectedItemIndex = data.selectedTransactionTypeIndex,
                onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                    events.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex
                    )
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(
                    items = data.transactionDataMappedByCategory,
                    key = { listItem ->
                        listItem.hashCode()
                    },
                ) { listItem ->
                    AnalysisListItem(
                        data = listItem,
                        events = AnalysisListItemEvents(),
                    )
                }
            }
        }
    }
}
