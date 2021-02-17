package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.database.WordDao
import java.lang.IllegalArgumentException

class UndefinedWordsViewModelFactory(private val dataSource: WordDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(UndefinedWordsViewModel::class.java)) {
            return UndefinedWordsViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}