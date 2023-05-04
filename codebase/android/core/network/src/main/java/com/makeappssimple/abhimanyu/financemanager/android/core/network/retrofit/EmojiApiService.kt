package com.makeappssimple.abhimanyu.financemanager.android.core.network.retrofit

import com.makeappssimple.abhimanyu.financemanager.android.core.network.model.NetworkEmoji
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EmojiApiService {
    @GET("emojis")
    suspend fun getEmojis(
        @Query("access_key") accessKey: String,
    ): List<NetworkEmoji>
}
