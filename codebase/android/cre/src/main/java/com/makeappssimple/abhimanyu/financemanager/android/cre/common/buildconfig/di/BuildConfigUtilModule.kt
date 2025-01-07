package com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.buildconfig.BuildConfigUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface BuildConfigUtilModule {
    @Binds
    public fun bindBuildConfigUtil(
        buildConfigUtilImpl: BuildConfigUtilImpl,
    ): BuildConfigUtil
}
