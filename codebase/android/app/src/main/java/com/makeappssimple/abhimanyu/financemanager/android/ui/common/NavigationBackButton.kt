package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.TopBarNavigationIconTint

@Composable
fun NavigationBackButton(
    navigationManager: NavigationManager,
) {
    IconButton(
        onClick = {
            navigateUp(
                navigationManager = navigationManager,
            )
        },
    ) {
        NavigationArrowBackIcon()
    }
}

@Composable
private fun NavigationArrowBackIcon() {
    Icon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = stringResource(
            id = R.string.navigation_icon_content_description,
        ),
        tint = TopBarNavigationIconTint,
    )
}
