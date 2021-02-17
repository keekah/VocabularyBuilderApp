package me.wingert.vocabularybuilder.undefinedwords

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import me.wingert.vocabularybuilder.database.WordDao

class UndefinedWordsViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

    val undefinedWords = database.getWordsWithNoDefinition()
}