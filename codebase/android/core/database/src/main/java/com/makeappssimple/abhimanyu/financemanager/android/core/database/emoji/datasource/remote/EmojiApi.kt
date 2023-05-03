package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.remote

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val EMOJI_API_BASE_URL = "https://emoji-api.com/"

@Singleton
class EmojiApi @Inject constructor() {
    private val moshi = Moshi.Builder()
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(EMOJI_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
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
