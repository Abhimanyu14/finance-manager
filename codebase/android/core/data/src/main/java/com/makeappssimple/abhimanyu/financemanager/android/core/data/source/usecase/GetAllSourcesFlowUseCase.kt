package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
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
