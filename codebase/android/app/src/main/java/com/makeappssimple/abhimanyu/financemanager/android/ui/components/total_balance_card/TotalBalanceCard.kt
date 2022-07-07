package com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalBalanceCard(
    viewModel: TotalBalanceCardViewModel = hiltViewModel<TotalBalanceCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
    val totalBalanceAmount by viewModel.sourcesTotalBalanceAmountValue.collectAsState(
        initial = 0L,
    )

    TotalBalanceCardView(
        data = TotalBalanceCardViewData(
            totalBalanceAmount = totalBalanceAmount,
            onClick = onClick,
        ),
    )
}
