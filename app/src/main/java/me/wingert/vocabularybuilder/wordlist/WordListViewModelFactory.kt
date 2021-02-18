package me.wingert.vocabularybuilder.wordlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.database.WordDao
import java.lang.IllegalArgumentException

class WordListViewModelFactory(private val dataSource: WordDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}