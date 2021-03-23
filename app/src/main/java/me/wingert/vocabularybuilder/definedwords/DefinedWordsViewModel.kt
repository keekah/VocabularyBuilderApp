package me.wingert.vocabularybuilder.definedwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDao

class DefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val definedWords = database.getDefinedWords()

    fun deleteWord(vocab: DatabaseVocabWord) {
        viewModelScope.launch {
            delete(vocab)
        }
    }

    private suspend fun delete(vocab: DatabaseVocabWord) {
        withContext(Dispatchers.IO) {
            database.deleteWord(vocab)
        }
    }

    fun onItemClick(vocab: DatabaseVocabWord) {
        Log.i("WordListViewModel", "Item clicked: $vocab")
    }

}