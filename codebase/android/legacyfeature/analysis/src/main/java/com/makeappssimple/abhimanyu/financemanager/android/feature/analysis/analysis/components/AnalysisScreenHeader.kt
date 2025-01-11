package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.actionbutton.ActionButtonEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event.AnalysisScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIState

@Composable
internal fun AnalysisScreenHeader(
    uiState: AnalysisScreenUIState,
    handleUIEvent: (uiEvent: AnalysisScreenUIEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            ),
    ) {
        MyHorizontalScrollingRadioGroup(
            modifier = Modifier
                .weight(
                    weight = 1F,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                ),
            data = MyHorizontalScrollingRadioGroupData(
                horizontalArrangement = Arrangement.Start,
                isLoading = uiState.isLoading,
                items = uiState.transactionTypesChipUIData,
                selectedItemIndex = uiState.selectedTransactionTypeIndex,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                        handleUIEvent(
                            AnalysisScreenUIEvent.OnTransactionTypeChange(
                                updatedSelectedTransactionTypeIndex = event.index,
                            )
                        )
                    }
                }
            },
        )
        ActionButton(
            data = ActionButtonData(
                isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                isLoading = uiState.isLoading,
                imageVector = MyIcons.FilterAlt,
                contentDescriptionStringResourceId = R.string.screen_analysis_filter_button_content_description,
            ),
            handleEvent = { event ->
                when (event) {
                    is ActionButtonEvent.OnClick -> {
                        handleUIEvent(AnalysisScreenUIEvent.OnFilterActionButtonClick)
                    }
                }
            },
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
}
