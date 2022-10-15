package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

class EmojiRepositoryImpl(
    emojiDao: EmojiDao,
) : EmojiRepository {
    override val emojis: Flow<List<EmojiLocalEntity>> = emojiDao.getEmojis()
}
