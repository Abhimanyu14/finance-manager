package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.BuildConfig
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.EmojiApi
import com.makeappssimple.abhimanyu.financemanager.android.models.Emoji

class EmojiRepositoryImpl : EmojiRepository {
    override suspend fun getEmojis(): List<Emoji> {
        val result = EmojiApi.retrofitService
            .getEmojis(
                accessKey = BuildConfig.OPEN_EMOJI_KEY,
            )
        return result
            .distinctBy {
                it.codePoint
            }
            .filter {
                !it.unicodeName.contains("skin tone")
            }
    }
}
