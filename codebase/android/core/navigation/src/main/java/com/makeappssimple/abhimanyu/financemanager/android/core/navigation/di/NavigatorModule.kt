package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class NavigatorModule {
    @Singleton
    @Provides
    public fun providesNavigator(
        @ApplicationScope coroutineScope: CoroutineScope,
    ): Navigator {
        return NavigatorImpl(
            coroutineScope = coroutineScope,
        )
    }
}
