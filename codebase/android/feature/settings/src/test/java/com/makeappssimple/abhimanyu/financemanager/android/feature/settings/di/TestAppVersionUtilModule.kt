package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.di.AppVersionUtilModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppVersionUtilModule::class],
)
class TestAppVersionUtilModule {
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
