package com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppVersionUtilModule {
    @Singleton
    @Provides
    fun providesAppVersionUtil(
        @ApplicationContext context: Context,
    ): AppVersionUtil {
        return AppVersionUtilImpl(
            context = context,
        )
    }
}
