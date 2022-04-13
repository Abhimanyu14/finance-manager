package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import javax.inject.Inject

class GetSourcesCountUseCase @Inject constructor(
    private val sourceRepository: SourceRepository,
) {
    suspend operator fun invoke(): Int {
        return sourceRepository.getSourcesCount()
    }
}
