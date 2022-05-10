package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import javax.inject.Inject

class DeleteAllEmojisUseCase @Inject constructor(
    private val emojiRepository: EmojiRepository,
) {
    suspend operator fun invoke() {
        return emojiRepository.deleteAllEmojis()
    }
}
