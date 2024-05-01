package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl.AlarmKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class AlarmKitModule {
    @Provides
    public fun providesAlarmKit(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider,
        dateTimeUtil: DateTimeUtil,
        myLogger: MyLogger,
        myPreferencesRepository: MyPreferencesRepository,
    ): AlarmKit {
        return AlarmKitImpl(
            context = context,
            dispatcherProvider = dispatcherProvider,
            dateTimeUtil = dateTimeUtil,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}
