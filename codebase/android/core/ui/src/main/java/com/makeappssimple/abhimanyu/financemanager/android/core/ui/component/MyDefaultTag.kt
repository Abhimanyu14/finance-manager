package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
fun MyDefaultTag(
    modifier: Modifier = Modifier,
) {
    MyText(
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(
                vertical = 1.dp,
                horizontal = 8.dp,
            ),
        text = stringResource(
            id = R.string.default_tag,
        ),
        style = MaterialTheme.typography.labelSmall
            .copy(
                color = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}
