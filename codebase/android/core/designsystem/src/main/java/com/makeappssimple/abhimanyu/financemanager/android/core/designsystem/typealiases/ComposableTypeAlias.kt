package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

typealias BoxScopedComposable = @Composable BoxScope.() -> Unit
typealias ColumnScopedComposable = @Composable ColumnScope.() -> Unit
typealias RowScopedComposable = @Composable RowScope.() -> Unit
