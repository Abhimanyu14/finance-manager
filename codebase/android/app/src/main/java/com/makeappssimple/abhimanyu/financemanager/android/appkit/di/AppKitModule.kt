package com.makeappssimple.abhimanyu.financemanager.android.appkit.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.appkit.AppKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.appkit.AppKit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppKitModule {
    @Singleton
    @Provides
    fun providesAppKit(
        @ApplicationContext context: Context,
    ): AppKit {
        return AppKitImpl(
            context = context,
        )
    }
}
