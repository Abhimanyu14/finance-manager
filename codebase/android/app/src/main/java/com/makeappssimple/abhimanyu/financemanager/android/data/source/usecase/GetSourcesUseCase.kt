package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSourcesUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    operator fun invoke(): Flow<List<Source>> {
        return sourceRepository.sources
    }
}
