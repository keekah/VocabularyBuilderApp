package me.wingert.vocabularybuilder.definedwords

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.room.Dao
import java.lang.IllegalArgumentException

class DefinedWordsViewModelFactory(private val dataSource: Dao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(DefinedWordsViewModel::class.java)) {
            return DefinedWordsViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}