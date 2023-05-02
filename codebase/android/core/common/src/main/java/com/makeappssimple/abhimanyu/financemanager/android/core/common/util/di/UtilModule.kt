package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.appversion.AppVersionUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.BuildConfigUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {
    @Provides
    fun providesAppVersionUtil(
        @ApplicationContext context: Context,
    ): AppVersionUtil {
        return AppVersionUtilImpl(
            context = context,
        )
    }

    @Provides
    fun providesBuildConfigUtil(): BuildConfigUtil {
        return BuildConfigUtilImpl()
    }
}
