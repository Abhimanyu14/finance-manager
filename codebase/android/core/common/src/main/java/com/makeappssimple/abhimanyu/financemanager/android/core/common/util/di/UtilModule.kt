package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.appversion.AppVersionUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {
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
