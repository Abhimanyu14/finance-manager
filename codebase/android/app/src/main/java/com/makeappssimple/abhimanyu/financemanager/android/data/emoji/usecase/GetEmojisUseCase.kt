package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
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
