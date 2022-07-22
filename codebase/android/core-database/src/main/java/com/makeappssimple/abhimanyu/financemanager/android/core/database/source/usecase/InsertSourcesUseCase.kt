package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface InsertSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class InsertSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : InsertSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        return sourceRepository.insertSources(
            sources = sources,
        )
    }
}
