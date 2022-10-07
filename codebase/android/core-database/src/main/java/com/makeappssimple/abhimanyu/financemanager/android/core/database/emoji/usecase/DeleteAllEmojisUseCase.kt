package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllEmojisUseCase {
    suspend operator fun invoke()
}

class DeleteAllEmojisUseCaseImpl(
    private val dataStore: MyDataStore,
    private val emojiRepository: EmojiRepository,
) : DeleteAllEmojisUseCase {
    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        return emojiRepository.deleteAllEmojis()
    }
}
