package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class NavigationModule {
    @Singleton
    @Provides
    public fun providesNavigator(
        @ApplicationScope coroutineScope: CoroutineScope,
    ): NavigationKit {
        return NavigationKitImpl(
            coroutineScope = coroutineScope,
        )
    }
}
