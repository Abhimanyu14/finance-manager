package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository

interface UpdateSourcesBalanceAmountUseCase {
    suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    )
}

class UpdateSourcesBalanceAmountUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val sourceRepository: SourceRepository,
) : UpdateSourcesBalanceAmountUseCase {
    override suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return sourceRepository.updateSourceBalanceAmount(
            sourcesBalanceAmountChange = sourcesBalanceAmountChange,
        )
    }
}
