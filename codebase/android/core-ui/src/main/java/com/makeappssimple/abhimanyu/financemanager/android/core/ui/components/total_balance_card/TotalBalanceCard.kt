package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TotalBalanceCard(
    viewModel: TotalBalanceCardViewModel = hiltViewModel<TotalBalanceCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
    val totalBalanceAmount by viewModel.sourcesTotalBalanceAmountValue.collectAsStateWithLifecycle(
        initialValue = 0L,
    )

    TotalBalanceCardView(
        totalBalanceAmount = totalBalanceAmount,
        onClick = onClick,
    )
}
