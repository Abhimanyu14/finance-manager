package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TotalBalanceCardViewModelImpl @Inject constructor(
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
) : TotalBalanceCardViewModel, ViewModel() {
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()
}
