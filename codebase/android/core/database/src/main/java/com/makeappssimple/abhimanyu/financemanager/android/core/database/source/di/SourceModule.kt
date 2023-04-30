package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesBalanceAmountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
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
    fun providesDeleteSourcesUseCase(
        dataStore: MyDataStore,
        sourceRepository: SourceRepository,
    ): DeleteSourcesUseCase {
        return DeleteSourcesUseCaseImpl(
            dataStore = dataStore,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetAllSourcesCountUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesCountUseCase {
        return GetAllSourcesCountUseCaseImpl(
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
    fun providesGetAllSourcesFlowUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesFlowUseCase {
        return GetAllSourcesFlowUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetAllSourcesUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesUseCase {
        return GetAllSourcesUseCaseImpl(
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
        dataStore: MyDataStore,
        sourceRepository: SourceRepository,
    ): InsertSourcesUseCase {
        return InsertSourcesUseCaseImpl(
            dataStore = dataStore,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesBalanceAmountUseCase(
        dataStore: MyDataStore,
        sourceRepository: SourceRepository,
    ): UpdateSourcesBalanceAmountUseCase {
        return UpdateSourcesBalanceAmountUseCaseImpl(
            dataStore = dataStore,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesUseCase(
        dataStore: MyDataStore,
        sourceRepository: SourceRepository,
    ): UpdateSourcesUseCase {
        return UpdateSourcesUseCaseImpl(
            dataStore = dataStore,
            sourceRepository = sourceRepository,
        )
    }
}
