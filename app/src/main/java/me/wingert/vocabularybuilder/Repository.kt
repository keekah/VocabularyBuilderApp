package me.wingert.vocabularybuilder

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.Api.retrofitService
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
            val wordList = retrofitService.getVocabularyWords()

            for (word in wordList)
            {
                if (database.wordDao.getWord(word.id) == null) {
                    database.wordDao.insert(DatabaseVocabWord(word.id, word.word, word.definition))
                    Log.i("Repository", "${word.id}  ${word.word} ${word.definition}")
                }
            }
        }
    }

    // Adds the word to the database for the first time.
    suspend fun addWord(word: String, definition: String?) {
        withContext(Dispatchers.IO) {
            var vocabWord =
                if (definition.isNullOrEmpty()) NetworkVocabWord(word, null)
                else NetworkVocabWord(word, definition)

            try {
                retrofitService.addWord(vocabWord)
            }
            catch (e: NetworkErrorException) {
                Log.d("Repository", "Failed to add word: $vocabWord. ${e.message}")
            }
        }
    }

    suspend fun deleteWord(vocabWord: VocabWord) {
        withContext(Dispatchers.IO) {
            try {
                retrofitService.deleteWord(vocabWord.id)
                database.wordDao.deleteWord(asDatabaseVocabWord(vocabWord))
                Log.i("Repository", "successfully deleted word: $vocabWord")
            }
            catch (e: Exception) {
                Log.d("Repository", "Error deleting word: $vocabWord: $e.message")
            }
        }
    }

}