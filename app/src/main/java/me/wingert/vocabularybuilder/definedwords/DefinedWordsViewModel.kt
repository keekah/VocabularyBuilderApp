package me.wingert.vocabularybuilder.definedwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.Repository
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDao
import me.wingert.vocabularybuilder.database.WordDatabase

class DefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val repository = Repository(WordDatabase.getInstance(application))

    val definedWords = repository.definedWords

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