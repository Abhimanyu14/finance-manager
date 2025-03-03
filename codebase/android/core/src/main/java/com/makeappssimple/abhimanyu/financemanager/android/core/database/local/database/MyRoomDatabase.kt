package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.constants.DatabaseConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.AutoDatabaseMigration
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.manualDatabaseMigrations
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import java.util.concurrent.Executors

@Database(
    version = DatabaseConstants.DATABASE_CURRENT_VERSION_NUMBER,
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        TransactionForEntity::class,
    ],
    autoMigrations = [
        AutoMigration(
            from = 20,
            to = 21,
            spec = AutoDatabaseMigration.AutoMigration20to21::class,
        ),
        AutoMigration(
            from = 11,
            to = 12,
            spec = AutoDatabaseMigration.AutoMigration11to12::class,
        ),
        AutoMigration(
            from = 10,
            to = 11,
            spec = AutoDatabaseMigration.AutoMigration10to11::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = AutoDatabaseMigration.AutoMigration5to6::class,
        ),
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoDatabaseMigration.AutoMigration1to2::class,
        ),
    ],
    exportSchema = true,
)
@TypeConverters(
    AmountConverter::class,
    IntListConverter::class,
    CategoryConverter::class,
)
public abstract class MyRoomDatabase : RoomDatabase() {
    public abstract fun categoryDao(): CategoryDao
    public abstract fun accountDao(): AccountDao
    public abstract fun transactionDao(): TransactionDao
    public abstract fun transactionForDao(): TransactionForDao

    public companion object {
        @Volatile
        private var instance: MyRoomDatabase? = null

        /**
         * Reference
         * https://github.com/android/app-actions-samples/blob/fea0f48a6d7f1c43d47c3ad14bfd11ace4b5629c/fitness-biis/starter/app/src/main/java/com/devrel/android/fitactions/model/FitDatabase.kt#L34
         */
        public fun getDatabase(
            context: Context,
            initialDatabasePopulator: InitialDatabasePopulator? = null,
        ): MyRoomDatabase {
            val tempInstance: MyRoomDatabase? = instance
            if (tempInstance.isNotNull()) {
                return tempInstance
            }
            return instance ?: synchronized(
                lock = this,
            ) {
                instance ?: buildDatabase(
                    context = context,
                    initialDatabasePopulator = initialDatabasePopulator
                ).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(
            context: Context,
            initialDatabasePopulator: InitialDatabasePopulator?,
        ): MyRoomDatabase {
            val roomDatabaseCallback: Callback = object : Callback() {
                override fun onCreate(
                    db: SupportSQLiteDatabase,
                ) {
                    // do something after database has been created
                }

                override fun onOpen(
                    db: SupportSQLiteDatabase,
                ) {
                    /*
                    // TODO-Abhi: Using Work Manager
                    val oneTimeWorkRequest: OneTimeWorkRequest =
                        OneTimeWorkRequestBuilder<InitialDatabasePopulationWorker>()
                            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                            .build()
                    WorkManager
                        .getInstance(context)
                        .enqueue(oneTimeWorkRequest)
                    */

                    // do something every time database is open
                    Executors
                        .newSingleThreadScheduledExecutor()
                        .execute {
                            val myRoomDatabase = getDatabase(
                                context = context,
                            )
                            initialDatabasePopulator?.populateInitialDatabaseData(
                                myRoomDatabase = myRoomDatabase,
                            )
                        }
                }
            }

            return Room
                .databaseBuilder(
                    context = context.applicationContext,
                    klass = MyRoomDatabase::class.java,
                    name = DatabaseConstants.DATABASE_NAME,
                )
                .addMigrations(
                    migrations = manualDatabaseMigrations,
                )
                .addCallback(
                    callback = roomDatabaseCallback,
                )
                .build()
        }
    }
}
