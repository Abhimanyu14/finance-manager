package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
fun NavigationBackButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        NavigationArrowBackIcon()
    }
}

@Composable
private fun NavigationArrowBackIcon() {
    Icon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = stringResource(
            id = R.string.navigation_back_button_navigation_icon_content_description,
        ),
        tint = MaterialTheme.colorScheme.primary,
    )
}
