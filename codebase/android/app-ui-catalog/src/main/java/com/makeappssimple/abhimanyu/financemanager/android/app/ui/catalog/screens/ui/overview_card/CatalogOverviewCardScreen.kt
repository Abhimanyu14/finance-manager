package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.overview_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
fun CatalogOverviewCardScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_overview_card,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            OverviewCard(
                data = OverviewCardData(
                    overviewTabSelectionIndex = 1,
                    pieChartData = PieChartData(
                        items = listOf(
                            PieChartItemData(
                                value = 500F,
                                text = "Income : ₹500",
                                color = MyColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = 600F,
                                text = "Expense : ₹600",
                                color = MyColor.ERROR,
                            ),
                        ),
                    ),
                    title = "2023",
                ),
                events = OverviewCardEvents(),
            )
            OverviewCard(
                data = OverviewCardData(
                    isLoading = true,
                ),
            )
        }
    }
}
