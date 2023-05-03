package com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.StringDecoderImpl
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
