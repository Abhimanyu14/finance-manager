package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository

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
