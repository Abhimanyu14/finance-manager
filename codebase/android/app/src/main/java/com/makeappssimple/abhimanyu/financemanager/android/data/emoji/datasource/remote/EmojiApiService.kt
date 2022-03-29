package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.remote

import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.EmojiRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface EmojiApiService {
    @GET("emojis")
    suspend fun getEmojis(
        @Query("access_key") accessKey: String,
    ): List<EmojiRemoteEntity>
}
