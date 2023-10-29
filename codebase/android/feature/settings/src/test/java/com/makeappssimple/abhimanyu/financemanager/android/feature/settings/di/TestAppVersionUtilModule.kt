package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.di.AppVersionUtilModule
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake.FakeAppVersionUtilImpl
import dagger.Module
import dagger.Provides
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
    fun providesAppVersionUtil(): AppVersionUtil {
        return FakeAppVersionUtilImpl()
    }
}
