package com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkitimpl.AlarmKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlarmKitModule {
    @Singleton
    @Provides
    fun providesAlarmKit(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        dateTimeUtil: DateTimeUtil,
        myLogger: MyLogger,
        myPreferencesRepository: MyPreferencesRepository,
    ): AlarmKit {
        return AlarmKitImpl(
            context = context,
            ioDispatcher = ioDispatcher,
            dateTimeUtil = dateTimeUtil,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}
