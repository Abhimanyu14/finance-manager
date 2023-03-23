package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface GetAllSourcesCountUseCase {
    suspend operator fun invoke(): Int
}

class GetAllSourcesCountUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetAllSourcesCountUseCase {
    override suspend operator fun invoke(): Int {
        return sourceRepository.getAllSourcesCount()
    }
}
