package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface UpdateSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class UpdateSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : UpdateSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        return sourceRepository.updateSources(
            sources = sources,
        )
    }
}
