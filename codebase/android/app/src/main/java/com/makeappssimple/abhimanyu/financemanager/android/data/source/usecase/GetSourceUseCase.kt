package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source

interface GetSourceUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Source?
}

class GetSourceUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : GetSourceUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Source? {
        return sourceRepository.getSource(
            id = id,
        )
    }
}
