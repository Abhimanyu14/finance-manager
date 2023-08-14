package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote

import android.content.Context
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

private const val UNICODE_EMOJIS_URL = "https://unicode.org/Public/emoji/15.0/emoji-test.txt"

class EmojiFetcher(
    private val context: Context,
) {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String = UNICODE_EMOJIS_URL,
    ) {
        val client = OkHttpClient()
            .newBuilder()
            .cache(
                cache = Cache(
                    directory = File(context.cacheDir, "http_cache"),
                    maxSize = (5 * 1024 * 1024).toLong(),
                ),
            )
            .build()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(
            request = request,
        ).enqueue(
            responseCallback = object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    callback.onFetchFailure(
                        errorMessage = e.message ?: "An error occurred",
                    )
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    callback.onFetchSuccess(
                        data = response.body?.string().orEmpty(),
                    )
                }
            },
        )
    }
}
