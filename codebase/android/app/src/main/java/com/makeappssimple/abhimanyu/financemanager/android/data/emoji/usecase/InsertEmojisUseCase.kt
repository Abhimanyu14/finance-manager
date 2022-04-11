package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import javax.inject.Inject

class InsertEmojisUseCase @Inject constructor(
    private val emojiRepository: EmojiRepository,
) {
    suspend operator fun invoke(
        vararg emojis: EmojiLocalEntity,
    ) {
        return emojiRepository.insertEmojis(
            emojis = emojis,
        )
    }
}
