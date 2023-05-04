package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

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
