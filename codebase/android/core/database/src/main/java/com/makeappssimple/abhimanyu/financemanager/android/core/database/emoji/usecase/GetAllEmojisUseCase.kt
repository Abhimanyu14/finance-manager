package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import kotlinx.coroutines.flow.Flow

interface GetAllEmojisUseCase {
    operator fun invoke(): Flow<List<EmojiLocalEntity>>
}

class GetAllAllEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : GetAllEmojisUseCase {
    override operator fun invoke(): Flow<List<EmojiLocalEntity>> {
        return emojiRepository.getAllEmojisFlow()
    }
}
