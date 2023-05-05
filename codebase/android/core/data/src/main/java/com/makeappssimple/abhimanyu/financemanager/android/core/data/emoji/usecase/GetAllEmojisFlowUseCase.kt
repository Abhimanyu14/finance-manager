package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

interface GetAllEmojisFlowUseCase {
    operator fun invoke(): Flow<List<EmojiLocalEntity>>
}

class GetAllEmojisFlowUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : GetAllEmojisFlowUseCase {
    override operator fun invoke(): Flow<List<EmojiLocalEntity>> {
        return emojiRepository.getAllEmojisFlow()
    }
}
