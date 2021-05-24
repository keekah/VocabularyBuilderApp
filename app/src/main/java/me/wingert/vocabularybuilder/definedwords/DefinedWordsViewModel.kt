package me.wingert.vocabularybuilder.definedwords

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

class DefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    private val repository = Repository(VocabularyBuilderDB.getInstance(application), application.applicationContext)
    val wordListDefined = repository.definedWords

    fun deleteWord(vocabWord: VocabWord) {
        viewModelScope.launch {
            repository.deleteWord(vocabWord)
        }
    }

    fun onItemClick(vocab: DatabaseVocabWord) {
        Log.i("WordListViewModel", "Item clicked: $vocab")
    }

}