package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.di

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationManagerModule {

    @Singleton
    @Provides
    fun providesNavigationManager(): NavigationManager = NavigationManagerImpl()
}
