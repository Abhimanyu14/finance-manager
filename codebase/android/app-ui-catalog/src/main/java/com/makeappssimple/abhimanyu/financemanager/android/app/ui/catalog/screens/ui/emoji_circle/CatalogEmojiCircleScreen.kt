package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.emoji_circle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogEmojiCircleScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onNavigationBackButtonClick = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_emoji_circle,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    isLoading = true,
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Small,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Normal,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
            )
            MyEmojiCircle(
                data = MyEmojiCircleData(
                    emojiCircleSize = EmojiCircleSize.Large,
                    backgroundColor = MaterialTheme.colorScheme.outline,
                    emoji = "😀",
                ),
            )
        }
    }
}
