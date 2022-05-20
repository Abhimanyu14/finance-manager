package com.makeappssimple.abhimanyu.financemanager.entities.emojis

import com.makeappssimple.abhimanyu.financemanager.entities.emoji.EmojiJsonEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmojisJsonEntity(
    override val versionNumber: Int,
    override val emojisData: List<EmojiJsonEntity>,
): Emojis
