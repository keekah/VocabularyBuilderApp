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

    val allWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getAllWords()) {
        it.asDomainModel()
    }

    val definedWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getDefinedWords()) {
        it.asDomainModel()
    }

    val undefinedWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getUndefinedWords()) {
        it.asDomainModel()
    }

    suspend fun getAllWords() {
        withContext(Dispatchers.IO) {
            val wordList = Api.retrofitService.getVocabularyWords()

            for (word in wordList)
            {
                if (database.wordDao.getWord(word.id) == null) {
                    database.wordDao.insert(DatabaseVocabWord(word.id, word.word, word.definition))
                    Log.i("Repository", "$word.id  $word.word $word.definition")
                }
            }
        }
    }


}