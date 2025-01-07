package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.analysis.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIState

@Composable
internal fun AnalysisScreenList(
    uiState: AnalysisScreenUIState,
) {
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    val maxAmountTextWidth = remember(
        uiState.analysisListItemData
    ) {
        if (uiState.analysisListItemData.isEmpty()) {
            0
        } else {
            uiState.analysisListItemData.maxOf {
                textMeasurer.measure(it.amountText).size.width
            }
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarLandscapeSpacer(),
    ) {
        items(
            items = uiState.analysisListItemData,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            AnalysisListItem(
                data = listItem.copy(
                    maxEndTextWidth = maxAmountTextWidth,
                ),
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
    }
}
