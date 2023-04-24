package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.components.action

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Composable
fun ActionView(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    @StringRes textStringResourceId: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .clip(
                MaterialTheme.shapes.medium,
            )
            .conditionalClickable(
                onClick = onClick,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .padding(
                    all = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
            MyText(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    )
                    .fillMaxWidth(),
                textStringResourceId = textStringResourceId,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }
    }
}
