package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface UpdateSourcesBalanceAmountUseCase {
    suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    )
}

class UpdateSourcesBalanceAmountUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : UpdateSourcesBalanceAmountUseCase {
    override suspend operator fun invoke(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return sourceRepository.updateSourceBalanceAmount(
            sourcesBalanceAmountChange = sourcesBalanceAmountChange,
        )
    }
}
