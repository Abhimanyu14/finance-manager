package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

@Composable
fun MyTopAppBar(
    navigationManager: NavigationManager,
    titleText: String,
    isNavigationIconVisible: Boolean,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                color = Primary,
            )
        },
        navigationIcon = {
            if (isNavigationIconVisible) {
                NavigationBackButton(
                    navigationManager = navigationManager,
                )
            }
        },
        modifier = Modifier
            .background(
                color = Surface,
            ),
    )
}
