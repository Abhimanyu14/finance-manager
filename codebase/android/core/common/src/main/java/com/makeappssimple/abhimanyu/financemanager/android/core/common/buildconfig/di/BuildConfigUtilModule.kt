package com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BuildConfigUtilModule {
    @Singleton
    @Binds
    fun bindBuildConfigUtil(
        buildConfigUtilImpl: BuildConfigUtilImpl,
    ): BuildConfigUtil
}
