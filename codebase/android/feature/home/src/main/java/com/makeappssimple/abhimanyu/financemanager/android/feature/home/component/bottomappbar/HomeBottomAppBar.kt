package com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomappbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarHeight
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

@Composable
internal fun HomeBottomAppBar(
    modifier: Modifier = Modifier,
    updateHomeBottomSheetType: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        cutoutShape = CircleShape,
        contentPadding = PaddingValues(
            bottom = navigationBarHeight(),
        ),
    ) {
        IconButton(
            onClick = {
                updateHomeBottomSheetType()
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = stringResource(
                    id = R.string.screen_home_bottom_app_bar_button_content_description,
                ),
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
    }
}
