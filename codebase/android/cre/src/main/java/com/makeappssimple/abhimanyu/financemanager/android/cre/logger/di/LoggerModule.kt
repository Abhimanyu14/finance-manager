package com.makeappssimple.abhimanyu.financemanager.android.cre.logger.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.MyLoggerImpl
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
