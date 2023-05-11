package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji

interface EmojiRepository {
    suspend fun getAllEmojis(): List<Emoji>
}
