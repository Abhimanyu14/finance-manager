package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import javax.inject.Inject

class UpdateSourcesUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    suspend operator fun invoke(
        vararg sources: Source,
    ) {
        return sourceRepository.updateSources(
            sources = sources,
        )
    }
}
