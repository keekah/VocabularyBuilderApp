package me.wingert.vocabularybuilder.allwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class AllWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val wordList = database.getAllWords()

    fun addWord(newWord: String, definition: String) {
        viewModelScope.launch {
            add(newWord, definition)
        }
    }

    // Add the word and its definition if one was provided
    private suspend fun add(word: String, definition: String) {
        withContext(Dispatchers.IO) {
            if (definition.isNotEmpty())
                database.insert(VocabularyWord(word = word, definition = definition))
            else
                database.insert(VocabularyWord(word = word))
        }
    }

    fun deleteWord(vocab: VocabularyWord) {
        viewModelScope.launch {
            delete(vocab)
        }
    }

    private suspend fun delete(vocab: VocabularyWord) {
        withContext(Dispatchers.IO) {
            database.deleteWord(vocab)
        }
    }

    fun onItemClick(vocab: VocabularyWord) {
        Log.i("WordListViewModel", "Item clicked: $vocab")
    }

}