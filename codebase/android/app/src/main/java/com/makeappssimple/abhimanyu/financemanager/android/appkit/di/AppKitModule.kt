package com.makeappssimple.abhimanyu.financemanager.android.appkit.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.appkit.AppKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.app.AppKit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class AppKitModule {
    @Provides
    public fun providesAppKit(
        @ApplicationContext context: Context,
    ): AppKit {
        return AppKitImpl(
            context = context,
        )
    }
}
