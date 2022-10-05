package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

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
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return sourceRepository.updateSourceBalanceAmount(
            sourcesBalanceAmountChange = sourcesBalanceAmountChange,
        )
    }
}
