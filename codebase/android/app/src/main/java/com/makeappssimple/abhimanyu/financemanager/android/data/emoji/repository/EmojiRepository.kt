package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.models.Emoji

interface EmojiRepository {
    suspend fun getEmojis(): List<Emoji>
}
