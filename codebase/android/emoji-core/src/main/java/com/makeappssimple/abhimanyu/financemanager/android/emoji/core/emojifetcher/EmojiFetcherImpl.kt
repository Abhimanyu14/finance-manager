package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.emojifetcher

import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

class EmojiFetcherImpl(
    private val cacheFile: File,
) : EmojiFetcher {
    override fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String,
    ) {
        val client = OkHttpClient()
            .newBuilder()
            .cache(
                cache = Cache(
                    directory = cacheFile,
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
