package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.annotation.StringRes
import com.makeappssimple.abhimanyu.financemanager.android.R

sealed class Screen(
    @StringRes val titleResourceId: Int,
    val route: String,
) {

    // App specific
    object Home : Screen(
        titleResourceId = R.string.screen_home,
        route = "home",
    )
}
