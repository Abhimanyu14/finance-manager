package com.makeappssimple.abhimanyu.financemanager.android.core.notificationkitimpl.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.appkit.AppKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkit.NotificationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.notificationkitimpl.NotificationKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class NotificationKitModule {
    @Singleton
    @Provides
    public fun providesNotificationKit(
        appKit: AppKit,
        @ApplicationContext context: Context,
        myLogger: MyLogger,
    ): NotificationKit {
        return NotificationKitImpl(
            appKit = appKit,
            context = context,
            myLogger = myLogger,
        )
    }
}
