package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {

    @Provides
    fun providesEmojiDao(
        myRoomDatabase: MyRoomDatabase,
    ): EmojiDao {
        return myRoomDatabase.emojiDao()
    }

    @Provides
    fun providesEmojiRepository(
        emojiDao: EmojiDao,
    ): EmojiRepository {
        return EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }

    @Provides
    fun providesGetAllEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): GetAllEmojisUseCase {
        return GetAllAllEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }
}
