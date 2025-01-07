package com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.cre.alarm.AlarmKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.MyLogger
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
        dateTimeUtil: DateTimeUtil,
        myLogger: MyLogger,
        myPreferencesRepository: MyPreferencesRepository,
    ): AlarmKit {
        return AlarmKitImpl(
            context = context,
            dateTimeUtil = dateTimeUtil,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}
