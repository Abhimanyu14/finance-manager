package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle

@Composable
internal fun CategoriesListItem(
    modifier: Modifier = Modifier,
    isDefault: Boolean,
    category: Category,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                all = 8.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 16.dp,
                ),
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
                    horizontal = 4.dp,
                ),
            text = category.title,
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = if (isDefault) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                ),
        )
    }
}
