package me.wingert.vocabularybuilder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDatabase
import me.wingert.vocabularybuilder.database.asDomainModel

class Repository(private val database: WordDatabase) {

    val wordList: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getAllWords()) {
        it.asDomainModel()
    }

    suspend fun getAllWords() {
        withContext(Dispatchers.IO) {
            val wordList = Api.retrofitService.getVocabularyWords()

            for (word in wordList)
            {
                database.wordDao.insert(DatabaseVocabWord(id = word.id, word = word.word, definition = word.definition))
                Log.i("Repository", "$word.id  $word.word $word.definition")
            }
        }
    }
}