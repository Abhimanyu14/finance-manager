package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
fun MyNavigationBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        NavigationArrowBackIcon()
    }
}

@Composable
private fun NavigationArrowBackIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
        contentDescription = stringResource(
            id = R.string.navigation_back_button_navigation_icon_content_description,
        ),
        tint = MaterialTheme.colorScheme.primary,
    )
}
