package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import kotlinx.coroutines.flow.Flow

interface GetSourcesUseCase {
    operator fun invoke(): Flow<List<Source>>
}

class GetSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourcesUseCase {
    override operator fun invoke(): Flow<List<Source>> {
        return sourceRepository.sources
    }
}
