package com.makeappssimple.abhimanyu.financemanager.entities.emojis

import com.makeappssimple.abhimanyu.financemanager.entities.emoji.EmojiLocalEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmojisLocalEntity(
    override val versionNumber: Int,
    override val emojisData: List<EmojiLocalEntity>,
) : Emojis {
    constructor(
        emojis: Emojis,
    ) : this(
        versionNumber = emojis.versionNumber + 1,
        emojisData = emojis.emojisData
            .filter { emoji ->
                !emoji.unicodeName.contains("skin tone")
            }
            .map {
                EmojiLocalEntity(it)
            },
    )
}
