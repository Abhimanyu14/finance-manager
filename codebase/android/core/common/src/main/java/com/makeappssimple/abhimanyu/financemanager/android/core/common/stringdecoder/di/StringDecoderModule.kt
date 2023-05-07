package com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StringDecoderModule {
    @Singleton
    @Binds
    fun bindStringDecoder(
        stringDecoderImpl: StringDecoderImpl,
    ): StringDecoder
}
