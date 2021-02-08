package me.wingert.vocabularybuilder

import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao
import me.wingert.vocabularybuilder.database.WordDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class WordDatabaseTest {

    private lateinit var wordDao : WordDao
    private lateinit var database : WordDatabase


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @Test
    @Throws(Exception::class)
    fun testUpdate() {
        wordDao.insert(VocabularyWord(word = "halcyon"))

        var vocab = wordDao.getWord("halcyon")
        val def = "denoting a period of time in the past that was idyllically happy and peaceful"
        vocab?.definition = def
        wordDao.update(vocab!!)

        assertEquals(vocab?.definition, def)
    }

    @Test
    @Throws(Exception::class)
    fun testGetWordsWithNoDefinition() {
        wordDao.getWordsWithNoDefinition().observeOnce {
            assertEquals(0, it.size)
        }
        wordDao.insert(VocabularyWord(word ="loosies"))
        wordDao.getWordsWithNoDefinition().observeOnce {
            assertEquals(1, it.size)
        }
    }

    // Thank you Alessandro Diaferia for all the code that follows
    // https://alediaferia.com/2018/12/17/testing-livedata-room-android/
    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }
}

class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)
    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}