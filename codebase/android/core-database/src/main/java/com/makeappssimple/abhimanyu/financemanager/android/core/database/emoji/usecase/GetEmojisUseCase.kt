package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import kotlinx.coroutines.flow.Flow

interface GetEmojisUseCase {
    operator fun invoke(): Flow<List<EmojiLocalEntity>>
}

class GetEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : GetEmojisUseCase {
    override operator fun invoke(): Flow<List<EmojiLocalEntity>> {
        return emojiRepository.emojis
    }
}
