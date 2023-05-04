package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.di

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
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
        )
    }

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

    @Provides
    fun providesGetAllCategoriesFlowUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesFlowUseCase {
        return GetAllCategoriesFlowUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesGetCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryUseCase {
        return GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

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
