package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSourcesTotalBalanceAmountValueUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    operator fun invoke(): Flow<Long> {
        return sourceRepository.sources.map {
            it.sumOf { source ->
                source.balanceAmount.value
            }
        }
    }
}
