package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Singleton
    @Provides
    fun providesCategoryDao(
        myRoomDatabase: MyRoomDatabase,
    ): CategoryDao {
        return myRoomDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
        )
    }

    @Singleton
    @Provides
    fun providesDeleteCategoryUseCase(
        dataStore: MyDataStore,
        categoryRepository: CategoryRepository,
    ): DeleteCategoryUseCase {
        return DeleteCategoryUseCaseImpl(
            dataStore = dataStore,
            categoryRepository = categoryRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllCategoriesFlowUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesFlowUseCase {
        return GetAllCategoriesFlowUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryUseCase {
        return GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Singleton
    @Provides
    fun providesInsertCategoriesUseCase(
        dataStore: MyDataStore,
        categoryRepository: CategoryRepository,
    ): InsertCategoriesUseCase {
        return InsertCategoriesUseCaseImpl(
            dataStore = dataStore,
            categoryRepository = categoryRepository,
        )
    }

    @Singleton
    @Provides
    fun providesUpdateCategoriesUseCase(
        dataStore: MyDataStore,
        categoryRepository: CategoryRepository,
    ): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCaseImpl(
            dataStore = dataStore,
            categoryRepository = categoryRepository,
        )
    }
}
