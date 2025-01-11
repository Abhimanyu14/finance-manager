package com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigKitImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface BuildConfigUtilModule {
    @Binds
    public fun bindBuildConfigUtil(
        buildConfigUtilImpl: BuildConfigKitImpl,
    ): BuildConfigKit
}
