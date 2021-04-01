package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
//import android.provider.Settings.Global.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.Repository
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDao
import me.wingert.vocabularybuilder.database.WordDatabase

class UndefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    private val repository = Repository(WordDatabase.getInstance(application))
    val undefinedWords = repository.undefinedWords

    fun addDefinition(vocabWord: VocabWord, definition: String) {
        viewModelScope.launch {
            vocabWord.definition = definition
            repository.updateWord(vocabWord)
        }
    }

    fun deleteWord(vocabWord: VocabWord) {
        viewModelScope.launch {
            repository.deleteWord(vocabWord)
        }
    }
}