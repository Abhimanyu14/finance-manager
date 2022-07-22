package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository

interface DeleteAllEmojisUseCase {
    suspend operator fun invoke()
}

class DeleteAllEmojisUseCaseImpl(
    private val emojiRepository: EmojiRepository,
) : DeleteAllEmojisUseCase {
    override suspend operator fun invoke() {
        return emojiRepository.deleteAllEmojis()
    }
}
