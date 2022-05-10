package com.makeappssimple.abhimanyu.financemanager.android.data.category.di

import com.makeappssimple.abhimanyu.financemanager.android.data.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.UpdateCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.MyRoomDatabase
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
    fun providesInsertCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): InsertCategoryUseCase {
        return InsertCategoryUseCaseImpl(
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
