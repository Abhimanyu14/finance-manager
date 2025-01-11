package com.makeappssimple.abhimanyu.financemanager.android.core.logger

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.NoOpLogKitImpl

public val LocalLogKit: ProvidableCompositionLocal<LogKit> = staticCompositionLocalOf {
    // Provide a default MyLogger which does nothing.
    // This is so that tests and previews do not have to provide one.
    // For real app builds provide a different implementation.
    NoOpLogKitImpl()
}
