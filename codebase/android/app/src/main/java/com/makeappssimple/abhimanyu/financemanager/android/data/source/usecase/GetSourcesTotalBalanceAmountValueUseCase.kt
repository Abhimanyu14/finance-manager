package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetSourcesTotalBalanceAmountValueUseCase {
    operator fun invoke(): Flow<Long>
}

class GetSourcesTotalBalanceAmountValueUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourcesTotalBalanceAmountValueUseCase {
    override operator fun invoke(): Flow<Long> {
        return sourceRepository.sources.map {
            it.sumOf { source ->
                source.balanceAmount.value
            }
        }
    }
}
