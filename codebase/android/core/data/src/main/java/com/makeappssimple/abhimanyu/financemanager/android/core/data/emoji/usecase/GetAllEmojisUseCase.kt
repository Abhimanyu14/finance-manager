package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity

interface GetAllEmojisUseCase {
    suspend operator fun invoke(): List<EmojiLocalEntity>
}

class GetAllEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : GetAllEmojisUseCase {
    override suspend operator fun invoke(): List<EmojiLocalEntity> {
        return emojiRepository.getAllEmojis()
    }
}
