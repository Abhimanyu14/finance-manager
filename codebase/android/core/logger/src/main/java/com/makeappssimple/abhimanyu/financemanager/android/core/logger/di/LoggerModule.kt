package com.makeappssimple.abhimanyu.financemanager.android.core.logger.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
    @Singleton
    @Provides
    fun providesLogger(
        buildConfigUtil: BuildConfigUtil,
    ): Logger {
        return LoggerImpl(
            buildConfigUtil = buildConfigUtil,
        )
    }
}
