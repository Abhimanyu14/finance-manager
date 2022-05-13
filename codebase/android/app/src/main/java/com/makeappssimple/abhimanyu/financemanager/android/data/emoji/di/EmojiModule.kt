package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.DeleteAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.DeleteAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.InsertEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.MyRoomDatabase
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
    fun providesDeleteAllEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): DeleteAllEmojisUseCase {
        return DeleteAllEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Provides
    fun providesGetEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): GetEmojisUseCase {
        return GetEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Provides
    fun providesInsertEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): InsertEmojisUseCase {
        return InsertEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }
}
