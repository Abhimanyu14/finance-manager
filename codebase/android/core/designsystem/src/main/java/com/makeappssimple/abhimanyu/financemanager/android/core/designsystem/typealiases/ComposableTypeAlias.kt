package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

typealias ComposableContent = @Composable () -> Unit
typealias NullableComposableContent = @Composable (() -> Unit)?

typealias BoxScopedComposableContent = @Composable BoxScope.() -> Unit
typealias ColumnScopedComposableContent = @Composable ColumnScope.() -> Unit
typealias RowScopedComposableContent = @Composable RowScope.() -> Unit
