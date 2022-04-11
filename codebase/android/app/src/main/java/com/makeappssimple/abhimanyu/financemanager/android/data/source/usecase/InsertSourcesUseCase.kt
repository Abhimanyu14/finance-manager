package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import javax.inject.Inject

class InsertSourcesUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    suspend operator fun invoke(
        vararg sources: Source,
    ) {
        return sourceRepository.insertSources(
            sources = sources,
        )
    }
}
