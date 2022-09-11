package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface UpdateSourcesBalanceAmountUseCase {
    suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    )
}

class UpdateSourcesBalanceAmountUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : UpdateSourcesBalanceAmountUseCase {
    override suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        return sourceRepository.updateSourceBalanceAmount(
            sourcesBalanceAmountChange = sourcesBalanceAmountChange,
        )
    }
}
