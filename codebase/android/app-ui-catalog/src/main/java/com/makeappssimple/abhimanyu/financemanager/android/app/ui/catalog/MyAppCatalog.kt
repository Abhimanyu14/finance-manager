package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.SaveButton

@Composable
fun MyAppCatalog() {
    MyAppTheme {
        Surface {
            val contentPadding = WindowInsets
                .systemBars
                .add(
                    WindowInsets(
                        left = 16.dp,
                        top = 16.dp,
                        right = 16.dp,
                        bottom = 16.dp,
                    )
                )
                .asPaddingValues()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                ),
            ) {
                item {
                    MyText(
                        text = "UI Catalog",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                item {
                    MyText(
                        text = "Emoji Circle",
                        modifier = Modifier.padding(
                            top = 16.dp,
                        ),
                    )
                }
                item {
                    FlowRow(
                        mainAxisSpacing = 16.dp,
                        crossAxisAlignment = FlowCrossAxisAlignment.Center,
                    ) {
                        EmojiCircle(
                            emoji = "😀",
                        )
                        EmojiCircle(
                            emoji = "😀",
                            emojiCircleSize = EmojiCircleSize.Normal,
                        )
                        EmojiCircle(
                            emoji = "😀",
                            backgroundColor = Color.LightGray,
                        )
                        EmojiCircle(
                            emoji = "😀",
                            backgroundColor = Color.LightGray,
                            emojiCircleSize = EmojiCircleSize.Normal,
                        )
                    }
                }
                item {
                    MyText(
                        text = "Save Button",
                        modifier = Modifier.padding(
                            top = 16.dp,
                        ),
                    )
                }
                item {
                    FlowRow(
                        mainAxisSpacing = 16.dp,
                        crossAxisAlignment = FlowCrossAxisAlignment.Center,
                    ) {
                        SaveButton(
                            textStringResourceId = R.string.save_button,
                            isEnabled = false,
                        ) {}
                        SaveButton(
                            textStringResourceId = R.string.save_button,
                            isEnabled = true,
                        ) {}
                    }
                }
            }
        }
    }
}
