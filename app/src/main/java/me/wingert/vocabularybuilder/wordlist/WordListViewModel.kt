package me.wingert.vocabularybuilder.wordlist

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class WordListViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val wordList = database.getAllWords()

    fun onAdd(newWord: Editable) {
        viewModelScope.launch {
            addWord(newWord.toString())
        }
    }

    private suspend fun addWord(word: String) {
        withContext(Dispatchers.IO) {
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