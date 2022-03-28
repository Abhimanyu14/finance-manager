package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {

    @Provides
    fun providesEmojiRepository(): EmojiRepository {
        return EmojiRepositoryImpl()
    }
}
