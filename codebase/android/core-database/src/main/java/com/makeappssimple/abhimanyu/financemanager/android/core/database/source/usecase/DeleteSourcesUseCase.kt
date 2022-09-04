package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface DeleteSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class DeleteSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : DeleteSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        return sourceRepository.deleteSources(
            sources = sources,
        )
    }
}
