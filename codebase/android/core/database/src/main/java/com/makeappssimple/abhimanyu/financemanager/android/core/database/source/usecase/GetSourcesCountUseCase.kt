package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface GetSourcesCountUseCase {
    suspend operator fun invoke(): Int
}

class GetSourcesCountUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourcesCountUseCase {
    override suspend operator fun invoke(): Int {
        return sourceRepository.getSourcesCount()
    }
}
