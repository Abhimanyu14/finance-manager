package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSourcesTotalBalanceAmountValueUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    operator fun invoke(): Flow<Long> {
        return sourceRepository.sourcesTotalBalanceAmountValue
    }
}
