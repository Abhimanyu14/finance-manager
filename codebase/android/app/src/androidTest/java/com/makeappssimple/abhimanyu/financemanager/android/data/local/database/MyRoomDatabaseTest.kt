package com.makeappssimple.abhimanyu.financemanager.android.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.financemanager.android.data.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.data.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.datasource.local.TransactionDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyRoomDatabaseTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var emojiDao: EmojiDao
    private lateinit var sourceDao: SourceDao
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .build()
        categoryDao = myRoomDatabase.categoryDao()
        emojiDao = myRoomDatabase.emojiDao()
        sourceDao = myRoomDatabase.sourceDao()
        transactionDao = myRoomDatabase.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        myRoomDatabase.close()
    }

    //    @Test
    //    @Throws(Exception::class)
    //    fun writeUserAndReadInList() {
    //        val user: User = TestUtil.createUser(3).apply {
    //            setName("george")
    //        }
    //        userDao.insert(user)
    //        val byName = userDao.findUsersByName("george")
    //        assertThat(byName.get(0), equalTo(user))
    //    }
}
