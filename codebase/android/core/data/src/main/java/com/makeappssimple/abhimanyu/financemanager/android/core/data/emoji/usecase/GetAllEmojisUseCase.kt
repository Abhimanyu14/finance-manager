package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji

interface GetAllEmojisUseCase {
    suspend operator fun invoke(): List<Emoji>
}

class GetAllEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : GetAllEmojisUseCase {
    override suspend operator fun invoke(): List<Emoji> {
        return emojiRepository.getAllEmojis()
    }
}
