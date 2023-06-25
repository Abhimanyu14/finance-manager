package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

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

    TotalBalanceCardUI(
        totalBalanceAmount = totalBalanceAmount,
        onClick = onClick,
    )
}
