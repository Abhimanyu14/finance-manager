package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity

interface InsertEmojisUseCase {
    suspend operator fun invoke(
        vararg emojis: EmojiLocalEntity,
    )
}

class InsertEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : InsertEmojisUseCase {
    override suspend operator fun invoke(
        vararg emojis: EmojiLocalEntity,
    ) {
        return emojiRepository.insertEmojis(
            emojis = emojis,
        )
    }
}
