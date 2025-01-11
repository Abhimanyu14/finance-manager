package com.makeappssimple.abhimanyu.financemanager.android.core.notification.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.app.AppKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.notification.NotificationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.notification.NotificationKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class NotificationKitModule {
    @Provides
    public fun providesNotificationKit(
        appKit: AppKit,
        @ApplicationContext context: Context,
        logKit: LogKit,
    ): NotificationKit {
        return NotificationKitImpl(
            appKit = appKit,
            context = context,
            logKit = logKit,
        )
    }
}
