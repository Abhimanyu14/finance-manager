package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.buildconfig.BuildConfigUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BuildConfigUtilModule {
    @Binds
    abstract fun bindBuildConfigUtil(
        buildConfigUtilImpl: BuildConfigUtilImpl,
    ): BuildConfigUtil
}
