package me.wingert.vocabularybuilder.definedwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class DefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val definedWords = database.getDefinedWords()

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