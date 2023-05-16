package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class EmojiDaoTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var emojiDao: EmojiDao

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        emojiDao = myRoomDatabase.emojiDao()
    }

    @After
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun getAllEmojis() = runTest {
        emojiDao.insertEmoji(
            emoji = testEmojis[0],
        )

        val result = emojiDao.getAllEmojis()

        Assert.assertEquals(
            1,
            result.size,
        )
        Assert.assertEquals(
            testEmojis[0],
            result[0],
        )
    }

    @Test
    fun insertEmoji_onConflictStrategy() = runTest {
        emojiDao.insertEmoji(
            emoji = testEmojis[0],
        )
        emojiDao.insertEmoji(
            emoji = testEmojis[1],
        )
        emojiDao.insertEmoji(
            emoji = testEmojis[1].copy(
                group = "test",
            ),
        )

        val result = emojiDao.getAllEmojis()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testEmojis[0],
            result[0],
        )
        Assert.assertEquals(
            testEmojis[1],
            result[1],
        )
    }

    @Test
    fun insertEmojis() = runTest {
        emojiDao.insertEmojis(
            *testEmojis.toTypedArray(),
        )

        val result = emojiDao.getAllEmojis()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testEmojis[0],
            result[0],
        )
        Assert.assertEquals(
            testEmojis[1],
            result[1],
        )
    }

    @Test
    fun getAllEmojisFlow() = runTest {
        emojiDao.insertEmojis(
            *testEmojis.toTypedArray(),
        )

        val result = emojiDao.getAllEmojisFlow().first()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            testEmojis[0],
            result[0],
        )
        Assert.assertEquals(
            testEmojis[1],
            result[1],
        )
    }

    @Test
    fun getAllEmojisCount() = runTest {
        emojiDao.insertEmojis(
            *testEmojis.toTypedArray(),
        )

        val result = emojiDao.getAllEmojisCount()

        Assert.assertEquals(
            testEmojis.size,
            result,
        )
    }

    @Test
    fun deleteAllEmojis() = runTest {
        emojiDao.insertEmojis(
            *testEmojis.toTypedArray(),
        )
        emojiDao.deleteAllEmojis()

        val result = emojiDao.getAllEmojisCount()

        Assert.assertEquals(
            0,
            result,
        )
    }

    companion object {
        private val testEmojis = listOf(
            EmojiEntity(
                character = "😀",
                codePoint = "1F600",
                group = "smileys-emotion",
                unicodeName = "grinning face",
            ),
            EmojiEntity(
                character = "😃",
                codePoint = "1F603",
                group = "smileys-emotion",
                unicodeName = "grinning face with big eyes",
            ),
        )
    }
}
