package me.wingert.vocabularybuilder.definedwords

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wingert.vocabularybuilder.network.Repository
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB

class DefinedWordsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(VocabularyBuilderDB.getInstance(application), application.applicationContext)
    val wordListDefined = repository.definedWords

    fun deleteWord(vocabWord: VocabWord) {
        viewModelScope.launch {
            repository.deleteWord(vocabWord)
        }
    }

}