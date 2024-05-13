package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class ActionButtonData(
    val isIndicatorVisible: Boolean = false,
    val imageVector: ImageVector,
    @StringRes val contentDescriptionStringResourceId: Int,
)
