package com.makeappssimple.abhimanyu.financemanager.entities.emojis

import com.makeappssimple.abhimanyu.financemanager.entities.emoji.Emoji

interface Emojis {
    val versionNumber: Int
    val emojisData: List<Emoji>
}
