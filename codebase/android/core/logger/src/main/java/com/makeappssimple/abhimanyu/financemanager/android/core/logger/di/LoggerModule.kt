package com.makeappssimple.abhimanyu.financemanager.android.core.logger.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public object LoggerModule {
    @Provides
    public fun providesLogger(
        buildConfigUtil: BuildConfigUtil,
    ): MyLogger {
        return MyLoggerImpl(
            buildConfigUtil = buildConfigUtil,
        )
    }
}
