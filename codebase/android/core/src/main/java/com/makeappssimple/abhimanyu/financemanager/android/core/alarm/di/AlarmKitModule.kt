package com.makeappssimple.abhimanyu.financemanager.android.core.alarm.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
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
        dateTimeKit: DateTimeKit,
        logKit: LogKit,
        myPreferencesRepository: MyPreferencesRepository,
    ): AlarmKit {
        return AlarmKitImpl(
            context = context,
            dateTimeKit = dateTimeKit,
            logKit = logKit,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}
