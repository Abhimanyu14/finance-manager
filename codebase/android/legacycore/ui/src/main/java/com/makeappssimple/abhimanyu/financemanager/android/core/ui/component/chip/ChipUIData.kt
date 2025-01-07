package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class ChipUIData(
    val isLoading: Boolean = false,
    val isMultiSelect: Boolean = false,
    val isSelected: Boolean = false,
    val borderColor: Color? = null,
    val textColor: Color? = null,
    val text: String = "",
    val icon: ImageVector? = null,
)
