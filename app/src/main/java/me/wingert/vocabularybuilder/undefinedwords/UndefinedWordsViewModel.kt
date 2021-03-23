package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
//import android.provider.Settings.Global.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDao

class UndefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val undefinedWords = database.getUndefinedWords()

    fun addDefinition(vocab: DatabaseVocabWord, definition: String) {
        viewModelScope.launch {
            definition.let {
                vocab.definition = definition
                updateWord(vocab)
            }

        }
    }

    private suspend fun updateWord(vocab: DatabaseVocabWord) {
        withContext(Dispatchers.IO) {
            database.update(vocab)
        }

    }

    fun delete(vocab: DatabaseVocabWord) {
        viewModelScope.launch {
            deleteWord(vocab)
        }
    }

    private suspend fun deleteWord(vocab: DatabaseVocabWord) {
        withContext(Dispatchers.IO) {
            database.deleteWord(vocab)
        }
    }
}