package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetSourcesTotalBalanceAmountValueUseCase {
    operator fun invoke(): Flow<Long>
}

class GetSourcesTotalBalanceAmountValueUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourcesTotalBalanceAmountValueUseCase {
    override operator fun invoke(): Flow<Long> {
        return sourceRepository.allSources.map {
            it.sumOf { source ->
                source.balanceAmount.value
            }
        }
    }
}
