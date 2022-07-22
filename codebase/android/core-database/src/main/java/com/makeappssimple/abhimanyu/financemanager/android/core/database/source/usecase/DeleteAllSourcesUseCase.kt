package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface DeleteAllSourcesUseCase {
    suspend operator fun invoke()
}

class DeleteAllSourcesUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : DeleteAllSourcesUseCase {
    override suspend operator fun invoke() {
        return sourceRepository.deleteAllSources()
    }
}
