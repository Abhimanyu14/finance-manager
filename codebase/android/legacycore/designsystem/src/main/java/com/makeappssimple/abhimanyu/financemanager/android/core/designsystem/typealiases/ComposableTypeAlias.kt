package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

public typealias ComposableContent = @Composable () -> Unit
public typealias NullableComposableContent = @Composable (() -> Unit)?

public typealias BoxScopedComposableContent = @Composable BoxScope.() -> Unit
public typealias ColumnScopedComposableContent = @Composable ColumnScope.() -> Unit
public typealias RowScopedComposableContent = @Composable RowScope.() -> Unit
