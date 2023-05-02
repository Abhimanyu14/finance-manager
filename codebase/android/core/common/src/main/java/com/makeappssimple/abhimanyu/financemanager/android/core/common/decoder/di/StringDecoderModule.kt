package com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder.StringDecoderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StringDecoderModule {
    @Binds
    abstract fun bindStringDecoder(
        stringDecoderImpl: StringDecoderImpl,
    ): StringDecoder
}
