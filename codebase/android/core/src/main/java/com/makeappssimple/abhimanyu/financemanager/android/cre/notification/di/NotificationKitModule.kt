package com.makeappssimple.abhimanyu.financemanager.android.cre.notification.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.cre.app.AppKit
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.cre.notification.NotificationKit
import com.makeappssimple.abhimanyu.financemanager.android.cre.notification.NotificationKitImpl
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
        myLogger: MyLogger,
    ): NotificationKit {
        return NotificationKitImpl(
            appKit = appKit,
            context = context,
            myLogger = myLogger,
        )
    }
}
