package com.makeappssimple.abhimanyu.financemanager.android.core.common.stringencoder.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringencoder.StringEncoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringencoder.StringEncoderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StringEncoderModule {
    @Singleton
    @Binds
    fun bindStringEncoder(
        stringEncoderImpl: StringEncoderImpl,
    ): StringEncoder
}
