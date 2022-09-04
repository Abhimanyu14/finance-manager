package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.UpdateCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {

    @Provides
    fun providesCategoryDao(
        myRoomDatabase: MyRoomDatabase,
    ): CategoryDao {
        return myRoomDatabase.categoryDao()
    }

    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
        )
    }

    @Provides
    fun providesDeleteAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): DeleteAllCategoriesUseCase {
        return DeleteAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesDeleteCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): DeleteCategoryUseCase {
        return DeleteCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesGetCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoriesUseCase {
        return GetCategoriesUseCaseImpl(
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
        categoryRepository: CategoryRepository,
    ): InsertCategoriesUseCase {
        return InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesUpdateCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }
}
