package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote

import androidx.emoji2.text.EmojiCompat
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EmojiDataSource(
    private val emojiFetcher: EmojiFetcher,
) {
    suspend fun getAllEmojis(): List<NetworkEmoji> {
        return suspendCoroutine { continuation ->
            emojiFetcher.fetchEmojiData(
                callback = object : EmojiFetchCallback {
                    override fun onFetchSuccess(
                        data: String,
                    ) {
                        val emojis = parseEmojiData(
                            data = data,
                        ).filter {
                            val isEmojiSupported = EmojiCompat.get().getEmojiMatch(
                                it.character,
                                Int.MAX_VALUE
                            ) == EmojiCompat.EMOJI_SUPPORTED
                            EmojiCompat.isConfigured() &&
                                    EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
                                    isEmojiSupported
                        }
                        continuation.resume(emojis)
                    }

                    override fun onFetchFailure(
                        errorMessage: String,
                    ) {
                        println("Fetch failed: $errorMessage")
                        continuation.resumeWithException(IOException("Fetch failed: $errorMessage"))
                    }
                },
            )
        }
    }
}
