package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.di.AppVersionUtilModule
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake.FakeAppVersionUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppVersionUtilModule::class],
)
public class TestAppVersionUtilModule {
    @Provides
    public fun providesAppVersionUtil(): AppVersionUtil {
        return FakeAppVersionUtilImpl()
    }
}
