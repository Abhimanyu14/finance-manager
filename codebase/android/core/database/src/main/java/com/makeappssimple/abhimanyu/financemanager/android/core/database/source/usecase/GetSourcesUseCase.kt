package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.flow.Flow

interface GetSourcesUseCase {
    operator fun invoke(): Flow<List<Source>>
}

class GetSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourcesUseCase {
    override operator fun invoke(): Flow<List<Source>> {
        return sourceRepository.allSources
    }
}
