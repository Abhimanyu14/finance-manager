package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface DeleteSourceUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteSourceUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : DeleteSourceUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        return sourceRepository.deleteSource(
            id = id,
        )
    }
}
