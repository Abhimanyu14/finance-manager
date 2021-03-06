package com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
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
