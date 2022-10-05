package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp

interface InsertEmojisUseCase {
    suspend operator fun invoke(
        vararg emojis: EmojiLocalEntity,
    )
}

class InsertEmojisUseCaseImpl(
    private val dataStore: MyDataStore,
    private val emojiRepository: EmojiRepository,
) : InsertEmojisUseCase {
    override suspend operator fun invoke(
        vararg emojis: EmojiLocalEntity,
    ) {
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return emojiRepository.insertEmojis(
            emojis = emojis,
        )
    }
}
