package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository

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
