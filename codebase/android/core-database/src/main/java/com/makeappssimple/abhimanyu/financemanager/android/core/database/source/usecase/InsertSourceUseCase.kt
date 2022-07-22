package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

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
