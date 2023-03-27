package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
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
