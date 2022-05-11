package com.makeappssimple.abhimanyu.financemanager.android.entities.emoji

import org.junit.Assert
import org.junit.Test

class EmojiLocalEntityTest {
    private val emojiRemoteEntity = EmojiRemoteEntity(
        character = "😀",
        codePoint = "1F600",
        group = "smileys-emotion",
        unicodeName = "grinning face",
    )

    private lateinit var emojiLocalEntity: EmojiLocalEntity

    @Test
    fun emojiRemoteEntity_to_emojiLocalEntity() {
        emojiLocalEntity = EmojiLocalEntity(
            emoji = emojiRemoteEntity,
        )

        Assert.assertEquals(
            emojiRemoteEntity.character,
            emojiLocalEntity.character,
        )
        Assert.assertEquals(
            emojiRemoteEntity.codePoint,
            emojiLocalEntity.codePoint,
        )
        Assert.assertEquals(
            emojiRemoteEntity.group,
            emojiLocalEntity.group,
        )
        Assert.assertEquals(
            emojiRemoteEntity.unicodeName,
            emojiLocalEntity.unicodeName,
        )
    }
}
