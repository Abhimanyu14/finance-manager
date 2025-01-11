package com.makeappssimple.abhimanyu.financemanager.android.core.logger.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public object LogModule {
    @Provides
    public fun providesLogger(
        buildConfigKit: BuildConfigKit,
    ): LogKit {
        return LogKitImpl(
            buildConfigKit = buildConfigKit,
        )
    }
}
