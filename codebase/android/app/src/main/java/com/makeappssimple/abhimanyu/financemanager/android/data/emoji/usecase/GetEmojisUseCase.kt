package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmojisUseCase @Inject constructor(
    private val emojiRepository: EmojiRepository,
) {
    operator fun invoke(): Flow<List<EmojiLocalEntity>> {
        return emojiRepository.emojis
    }
}
