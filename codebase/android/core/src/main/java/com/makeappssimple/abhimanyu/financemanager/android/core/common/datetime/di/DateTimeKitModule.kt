package com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKitImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface DateTimeKitModule {
    @Binds
    public fun bindDateTimeUtil(
        dateTimeUtilImpl: DateTimeKitImpl,
    ): DateTimeKit
}
