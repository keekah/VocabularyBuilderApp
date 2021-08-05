package me.wingert.vocabularybuilder.allwords

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wingert.vocabularybuilder.network.Repository
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.room.WordDao
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import java.io.IOException

const val GO_BEARS = "GO BEARS!"

class AllWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    private val repository = Repository(VocabularyBuilderDB.getInstance(application), application.applicationContext)
    val wordListAll = repository.allWords

    init {
        refreshRepository()
    }

    private fun refreshRepository() {
        viewModelScope.launch {
            try {
                repository.getAllWords(true)
            }
            catch (networkError: IOException) {
                // TODO fill in
            }
        }
    }

    // Called when the word is equal to the Noorvik zip code (99763).
    fun cheerForBears(word: String, definition: String?) {
        val def =
            if (definition.isNullOrEmpty()) GO_BEARS
            else "$definition; $GO_BEARS"

        addWord(word, def)
    }

    fun addWord(word: String, definition: String?) {
        viewModelScope.launch {
            val vocabWord =
                if (definition.isNullOrEmpty()) VocabWord(word = word, definition = null)
                else VocabWord(word = word, definition = definition)

            repository.updateWord(vocabWord)
        }
    }

    fun deleteWord(vocabWord: VocabWord) {
        viewModelScope.launch {
            repository.deleteWord(vocabWord)
        }
    }

}