package me.wingert.vocabularybuilder.network

import android.accounts.NetworkErrorException
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.asDatabaseVocabWord
import me.wingert.vocabularybuilder.asNetworkVocabWord
import me.wingert.vocabularybuilder.network.Api.retrofitService
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import me.wingert.vocabularybuilder.room.asDomainModel

class Repository(private val database: VocabularyBuilderDB, context: Context) {

    private val sessionManager = SessionManager(context)

    val allWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getAllWords()) {
        it.asDomainModel()
    }

    val definedWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getDefinedWords()) {
        it.asDomainModel()
    }

    val undefinedWords: LiveData<List<VocabWord>> = Transformations.map(database.wordDao.getUndefinedWords()) {
        it.asDomainModel()
    }


    // The init parameter indicates whether the local cache is being populated for the first time.
    suspend fun getAllWords(init: Boolean) {
        withContext(Dispatchers.IO) {
            val authToken = sessionManager.fetchAuthToken();
            Log.i("Repository", "getAllWords() called...q")
            val wordList = retrofitService.getVocabularyWords(token = authToken!!)
            val databaseWordList = wordList.map { asDatabaseVocabWord(it) }
            Log.i("Repository", "wordListRetrieved")

            when (init) {
                true -> {
                    database.wordDao.clear()
                    database.wordDao.insertAll(databaseWordList)
                }
                false -> {
                    updateLocalCache(wordList)
                    database.wordDao.insertAll(databaseWordList)
                }
            }
        }
    }

    // Update the local cache so that it is in sync with the latest data pulled from the network.
    private suspend fun updateLocalCache(networkWords : List<VocabWord>) {
        withContext(Dispatchers.IO) {

            // For efficiency, create a map of the vocabulary words that are retrieved from the network.
            val networkWordMap = HashMap<Int, VocabWord>()
            for (word in networkWords)
                networkWordMap[word.id] = word

            // Do the same for the words in our local cache. This time, a set will do.
            val cachedWords = database.wordDao.getWords()
            val cachedIds = HashSet<Int>()
            for (word in cachedWords)
                cachedIds.add(word.id)

            // Find the words from networkWords (the most recent data) that are not in the local
            // cache and add them to the local cache. If a word is in both, add it to a list of
            // words that need to be checked for updates.
            val wordsToCheckForUpdates = mutableListOf<VocabWord>()
            for (id in networkWordMap.keys) {
                if (!cachedIds.contains(id))
                    database.wordDao.insert(asDatabaseVocabWord(networkWordMap[id]!!))
                else
                    wordsToCheckForUpdates.add(networkWordMap[id]!!)
            }

            // Find the words stored locally that are not in the list of networkWords and delete
            // them from the local cache.
            for (id in cachedIds) {
                if (networkWordMap[id] == null)
                    database.wordDao.deleteWord(database.wordDao.getWord(id)!!)
            }

            // Check the shared words for updates to their definitions. Update the local cache
            // according to the data in the networkWords, as that is the most recent data. If a word
            // was ever given a definition, it will be contained in the network word.
            for (word in wordsToCheckForUpdates) {
                val new = networkWordMap[word.id]
                val old = database.wordDao.getWord(word.id)
                if (new!!.definition != old!!.definition) {
                    old.definition = new.definition
                    database.wordDao.update(old)
                }
            }
        }
    }

    // Delete the word from the web service and then update the local cache.
    suspend fun deleteWord(vocabWord: VocabWord) {
        withContext(Dispatchers.IO) {
            val authToken = sessionManager.fetchAuthToken()
            try {
                retrofitService.deleteWord(authToken!!, vocabWord.word)
                getAllWords(false)
            }
            catch (e: Exception) {
                Log.d("Repository", "Failed to delete word: $vocabWord. ${e.message}")
            }
        }
    }

    // This method is called when adding a word to the list for the first time or when retroactively
    // adding a definition to a word that is already in the list.
    // The VocabWord passed is guaranteed to contain a word; it may or may not contain a definition.
    suspend fun updateWord(vocabWord: VocabWord) {
        withContext(Dispatchers.IO) {
            val authToken = sessionManager.fetchAuthToken()

            try {
                retrofitService.updateWord(authToken!!, asNetworkVocabWord(vocabWord))
                getAllWords(false)
            }
            catch (e: NetworkErrorException) {
                database.wordDao.insert(asDatabaseVocabWord(vocabWord))
                Log.d("Repository", "Failed to update word: $vocabWord. ${e.message}")
            }
        }
    }

}



