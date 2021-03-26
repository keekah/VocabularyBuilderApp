package me.wingert.vocabularybuilder.allwords

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.Api
import me.wingert.vocabularybuilder.Post
import me.wingert.vocabularybuilder.Repository
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDao
import me.wingert.vocabularybuilder.database.WordDatabase
import java.io.IOException
import java.lang.Exception

class AllWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

//    val wordList = database.getAllWords()

    private lateinit var _allWords : List<VocabWord>
//    val allWords : LiveData<List<VocabWord>>
//        get() = _allWords

    val repository = Repository(WordDatabase.getInstance(application))

    val wordList = repository.allWords

    init {
        getVocabularyWords()
        refreshRepository()
    }

    private fun refreshRepository() {
        viewModelScope.launch {
            try {
                repository.getAllWords()
            }
            catch (networkError: IOException) {
                Log.d("AllWordsVM", "Failed to refresh repository: ${networkError.message}")
            }
        }
    }

//    fun addWord(newWord: String, definition: String) {
//        viewModelScope.launch {
//            add(newWord, definition)
//        }
//    }

    fun addWord(word: String, definition: String?) {
        Log.d("AWVM", "addWord called")
        viewModelScope.launch {
            try {
                if (definition.isNullOrEmpty())
                    Api.retrofitService.savePost(Post(903, word, null))

                else
                    Api.retrofitService.savePost(Post(903, word, definition))
                Log.d("AllWordsVM", "post saved: $word $definition")
            }
            catch (e: Exception) {
                Log.d("AllWordsVM", "${e.message}")
            }
        }
    }

    // Add the word and its definition if one was provided.
    // If the word was already stored, update its definition (appending if one already existed)
    private suspend fun add(word: String, definition: String) {
        withContext(Dispatchers.IO) {
            if (definition.isNotEmpty()) {
                var storedWord = database.getWord(word)

                if (storedWord == null)
                    database.insert(DatabaseVocabWord(word = word, definition = definition))
                else {
                    if (storedWord.definition != null) {
                        var newDef = storedWord.definition + "; " + definition
                        storedWord.definition = newDef
                    }
                    else {
                        storedWord.definition = definition
                    }

                    database.update(storedWord)
                }
            }
            else
                database.insert(DatabaseVocabWord(word = word))
        }
    }

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

    private fun getVocabularyWords() {
        viewModelScope.launch {
            try {
                _allWords = Api.retrofitService.getVocabularyWords()
                Log.i("AllWordsVM", "Retrieved ${_allWords.size} vocabulary words")
            }
            catch (e: Exception) {
                Log.i("AllWordsVM", "Error: ${e.message}")
                _allWords = ArrayList()
            }
        }
    }

}