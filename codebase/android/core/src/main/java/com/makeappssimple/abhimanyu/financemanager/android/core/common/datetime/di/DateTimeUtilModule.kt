package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface DateTimeUtilModule {
    @Binds
    public fun bindDateTimeUtil(
        dateTimeUtilImpl: DateTimeUtilImpl,
    ): DateTimeUtil
}
