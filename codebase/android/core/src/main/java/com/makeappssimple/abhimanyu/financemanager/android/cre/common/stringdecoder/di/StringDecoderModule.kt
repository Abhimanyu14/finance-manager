package com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder.StringDecoderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface StringDecoderModule {
    @Binds
    public fun bindStringDecoder(
        stringDecoderImpl: StringDecoderImpl,
    ): StringDecoder
}
