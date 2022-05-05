package com.makeappssimple.abhimanyu.financemanager.android.data.source.di

import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SourceModule {

    @Provides
    fun providesSourceDao(
        myRoomDatabase: MyRoomDatabase,
    ): SourceDao {
        return myRoomDatabase.sourceDao()
    }

    @Provides
    fun providesSourceRepository(
        sourceDao: SourceDao,
    ): SourceRepository {
        return SourceRepositoryImpl(
            sourceDao = sourceDao,
        )
    }

    @Provides
    fun providesDeleteSourceUseCase(
        sourceRepository: SourceRepository,
    ): DeleteSourceUseCase {
        return DeleteSourceUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesCountUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesCountUseCase {
        return GetSourcesCountUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesTotalBalanceAmountValueUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesTotalBalanceAmountValueUseCase {
        return GetSourcesTotalBalanceAmountValueUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesUseCase {
        return GetSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourceUseCase(
        sourceRepository: SourceRepository,
    ): GetSourceUseCase {
        return GetSourceUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesInsertSourcesUseCase(
        sourceRepository: SourceRepository,
    ): InsertSourcesUseCase {
        return InsertSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesInsertSourceUseCase(
        sourceRepository: SourceRepository,
    ): InsertSourceUseCase {
        return InsertSourceUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesUseCase(
        sourceRepository: SourceRepository,
    ): UpdateSourcesUseCase {
        return UpdateSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }
}
