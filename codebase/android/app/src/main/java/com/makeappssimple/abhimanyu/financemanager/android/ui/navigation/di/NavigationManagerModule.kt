package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.di

import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.NavigationManager
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
    fun providesNavigationManager() = NavigationManager()
}
