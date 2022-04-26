package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetEmojisUseCase @Inject constructor(
    private val emojiRepository: EmojiRepository,
) {
    operator fun invoke(): Flow<List<EmojiLocalEntity>> {
        return emojiRepository.emojis
    }
}
