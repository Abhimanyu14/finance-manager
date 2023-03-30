package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.flow.Flow

interface GetAllSourcesFlowUseCase {
    operator fun invoke(): Flow<List<Source>>
}

class GetAllSourcesFlowUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetAllSourcesFlowUseCase {
    override operator fun invoke(): Flow<List<Source>> {
        return sourceRepository.getAllSourcesFlow()
    }
}