package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle

data class CategoriesGridItemData(
    val isSelected: Boolean,
    val category: Category,
)

@Composable
fun CategoriesGridItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    category: Category,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 16.dp,
                ),
            )
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                },
            )
            .clickable {
                onClick()
            },
    ) {
        MyEmojiCircle(
            emojiCircleSize = EmojiCircleSize.Large,
            emoji = category.emoji,
        )
        MyText(
            modifier = Modifier
                .padding(
                    start = 6.dp,
                    end = 6.dp,
                    bottom = 4.dp,
                ),
            text = category.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    textAlign = TextAlign.Center,
                ),
        )
    }
}
