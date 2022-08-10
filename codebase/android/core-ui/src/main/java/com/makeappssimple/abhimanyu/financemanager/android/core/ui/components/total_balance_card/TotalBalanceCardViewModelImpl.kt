package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class TotalBalanceCardViewModelImpl @Inject constructor(
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
) : TotalBalanceCardViewModel, ViewModel() {
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()
}