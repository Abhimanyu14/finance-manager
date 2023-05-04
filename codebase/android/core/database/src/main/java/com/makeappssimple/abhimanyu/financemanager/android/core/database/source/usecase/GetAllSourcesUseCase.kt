package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

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
