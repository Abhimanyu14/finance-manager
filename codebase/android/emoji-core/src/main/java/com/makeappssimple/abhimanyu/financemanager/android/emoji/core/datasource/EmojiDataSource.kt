package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.datasource

import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.model.NetworkEmoji

interface EmojiDataSource {
    suspend fun getAllEmojis(): List<NetworkEmoji>
}
