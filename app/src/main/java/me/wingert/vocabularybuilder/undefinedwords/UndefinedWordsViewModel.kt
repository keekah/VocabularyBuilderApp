package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
import android.util.Log
//import android.provider.Settings.Global.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class UndefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val undefinedWords = database.getUndefinedWords()

    fun addDefinition(vocab: VocabularyWord, definition: String) {
        viewModelScope.launch {
            definition.let {
                vocab.definition = definition
                updateWord(vocab)
            }

        }
    }

    private suspend fun updateWord(vocab: VocabularyWord) {
        withContext(Dispatchers.IO) {
            database.update(vocab)
        }

    }

    fun delete(vocab: VocabularyWord) {
        viewModelScope.launch {
            deleteWord(vocab)
        }
    }

    private suspend fun deleteWord(vocab: VocabularyWord) {
        withContext(Dispatchers.IO) {
            database.deleteWord(vocab)
        }
    }
}