package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class TotalBalanceCardViewModelImpl @Inject constructor(
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
) : TotalBalanceCardViewModel, ViewModel() {
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()
}
