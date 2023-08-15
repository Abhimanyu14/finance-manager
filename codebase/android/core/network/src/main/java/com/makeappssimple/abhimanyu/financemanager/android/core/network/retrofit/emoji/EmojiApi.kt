package com.makeappssimple.abhimanyu.financemanager.android.core.network.retrofit.emoji

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

private const val EMOJI_API_BASE_URL = "https://emoji-api.com/"

@Singleton
class EmojiApi(
    json: Json,
) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(EMOJI_API_BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: EmojiApiService by lazy {
        retrofit.create(EmojiApiService::class.java)
    }

    suspend fun getEmojis(
        accessKey: String,
    ) {
        retrofitService.getEmojis(
            accessKey = accessKey,
        )
    }
}
