package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class UndefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val undefinedWords = database.getUndefinedWords()

    fun addDefinition(vocab: VocabularyWord, definition: String) {
        viewModelScope.launch {
            definition?.let {
                Log.i("UndefVM-addDefinition", "vocab = $vocab")
                vocab.definition = definition
                updateWord(vocab)
            }

        }
    }

    private suspend fun updateWord(vocab: VocabularyWord) {
        withContext(Dispatchers.IO) {
            database.update(vocab)
            Log.i("UndefVM-updateWord", "vocab = $vocab")
        }

    }
}