package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.total_balance_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData

@Composable
internal fun CatalogTotalBalanceCardScreen(
    navigateUp: () -> Unit,
) {
    MyScaffold(
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_total_balance_card,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 8.dp,
                ),
        ) {
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    totalBalanceAmount = 1234567890,
                ),
            )
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    isBalanceVisible = true,
                    totalBalanceAmount = 1234567890,
                ),
            )
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    isLoading = true,
                    totalBalanceAmount = 1234567890,
                ),
            )
        }
    }
}
