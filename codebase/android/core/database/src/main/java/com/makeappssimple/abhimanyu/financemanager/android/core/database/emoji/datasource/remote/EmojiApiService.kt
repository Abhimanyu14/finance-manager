package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.remote

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EmojiApiService {
    @GET("emojis")
    suspend fun getEmojis(
        @Query("access_key") accessKey: String,
    ): List<EmojiRemoteEntity>
}
