package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.navigation_back_button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
fun MyNavigationBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    MyIconButton(
        modifier = modifier,
        imageVector = MyIcons.ArrowBack,
        contentDescription = stringResource(
            id = R.string.navigation_back_button_navigation_icon_content_description,
        ),
        tint = MaterialTheme.colorScheme.primary,
        onClick = onClick,
    )
}
