package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.remote

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://emoji-api.com/"

private val moshi = Moshi.Builder()
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object EmojiApi {
    val retrofitService: EmojiApiService by lazy {
        retrofit.create(EmojiApiService::class.java)
    }
}
