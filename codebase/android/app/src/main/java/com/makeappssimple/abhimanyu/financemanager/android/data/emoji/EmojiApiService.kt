package com.makeappssimple.abhimanyu.financemanager.android.data.emoji

import com.makeappssimple.abhimanyu.financemanager.android.models.Emoji
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://emoji-api.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EmojiApiService {
    @GET("emojis")
    suspend fun getEmojis(
        @Query("access_key") accessKey: String,
    ): List<Emoji>
}

object EmojiApi {
    val retrofitService: EmojiApiService by lazy {
        retrofit.create(EmojiApiService::class.java)
    }
}
