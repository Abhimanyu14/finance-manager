package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository

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
