package me.wingert.vocabularybuilder.allwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wingert.vocabularybuilder.Repository
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.room.DatabaseVocabWord
import me.wingert.vocabularybuilder.room.WordDao
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import java.io.IOException

class AllWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    private val repository = Repository(VocabularyBuilderDB.getInstance(application))
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
                Log.d("AllWordsVM", "Failed to refresh repository: ${networkError.message}")
            }
        }
    }

    fun addWord(word: String, definition: String?) {
        Log.d("AWVM", "addWord called")
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

    fun onItemClick(vocab: DatabaseVocabWord) {
        Log.i("WordListViewModel", "Item clicked: $vocab")
    }

}