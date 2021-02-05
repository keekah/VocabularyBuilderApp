package me.wingert.vocabularybuilder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WordListViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}