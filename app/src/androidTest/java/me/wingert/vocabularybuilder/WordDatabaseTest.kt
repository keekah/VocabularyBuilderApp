package me.wingert.vocabularybuilder

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao
import me.wingert.vocabularybuilder.database.WordDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class WordDatabaseTest {

    private lateinit var wordDao : WordDao
    private lateinit var database : WordDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(context, WordDatabase::class.java).allowMainThreadQueries().build()
        wordDao = database.wordDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsert() {
        val vocab = VocabularyWord(word = "hirsute")
        wordDao.insert(vocab)
        val retrievedWord = wordDao.getMostRecentWord()

        assertEquals(vocab.word, retrievedWord?.word)
    }
}