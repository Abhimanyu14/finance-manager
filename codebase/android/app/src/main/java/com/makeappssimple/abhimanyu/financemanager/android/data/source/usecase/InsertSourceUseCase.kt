package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source

interface InsertSourceUseCase {
    suspend operator fun invoke(
        source: Source,
    )
}

class InsertSourceUseCaseImpl(
    private val sourceRepository: SourceRepository,
) : InsertSourceUseCase {
    override suspend operator fun invoke(
        source: Source,
    ) {
        return sourceRepository.insertSource(
            source = source,
        )
    }
}
