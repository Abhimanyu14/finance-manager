package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source

interface GetAllSourcesUseCase {
    suspend operator fun invoke(): List<Source>
}

class GetAllSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetAllSourcesUseCase {
    override suspend operator fun invoke(): List<Source> {
        return sourceRepository.getAllSources()
    }
}
