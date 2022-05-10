package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository

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
