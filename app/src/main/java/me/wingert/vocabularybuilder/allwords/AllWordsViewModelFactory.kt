package me.wingert.vocabularybuilder.allwords

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.database.WordDao
import java.lang.IllegalArgumentException

class AllWordsViewModelFactory(private val dataSource: WordDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AllWordsViewModel::class.java)) {
            return AllWordsViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}