package me.wingert.vocabularybuilder

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDao

class WordListViewModel(val database: WordDao, application: Application) : AndroidViewModel(application) {

//    private val _wordList = MutableLiveData<List<String>>()
//    val wordList : LiveData<List<String>>
//        get() = _wordList

    val wordList = database.getAllWords()

    init {
//        populateWordList()
//        Log.i("WordListViewModel.kt", "_newWord . value = ${_newWord.value.toString()}")
    }


//    fun onAdd(newWord: Editable) {
//        // check if word is already in list
//        // if not, add the word
//        // update
//        Log.i("WordListViewModel", "$newWord added to list")
//        _wordList.value = _wordList.value?.plus(newWord.toString())
//    }

    fun onAdd(newWord: Editable) {
        viewModelScope.launch {
            addWord(newWord.toString())
        }
    }

    private suspend fun addWord(word: String) {
        withContext(Dispatchers.IO) {
            database.insert(VocabularyWord(word = word))
        }
    }

//    fun finishedAdding() {
//        _newWord.value = ""
//    }
//
//    private fun populateWordList() {
//        _wordList.value = mutableListOf(
//            "hirsute",
//            "milieu",
//            "halcyon",
//            "screed",
//            "excoriate",
//            "lambaste",
//            "poky",
//            "loosies",
//            "nascent",
//            "reticent",
//            "curative",
//            "akimbo",
//            "artifice",
//            "panniers",
//            "necropolitics",
//            "cadre",
//            "posterity",
//            "metathesis",
//            "sacrosanct",
//            "maieutic",
//            "convocation",
//            "boughs",
//            "canonically",
//            "intimated",
//            "commensurate",
//            "thrall",
//            "enervation",
//            "stanch",
//            "aquiline",
//            "rapacious",
//            "brogues",
//            "pied-à-terre ",
//            "replete",
//            "credenza",
//            "requisite",
//            "extolling",
//            "simulacrum",
//            "meritocracy",
//            "entail",
//            "pernicious",
//            "perfunctorily",
//            "paragon",
//            "ethos",
//            "banal",
//            "yoked",
//            "aviophobe",
//            "reactionary",
//            "ad hoc ",
//            "cohort",
//            "fraught",
//            "staid",
//            "nootropic",
//            "fatuous",
//            "scrim",
//            "dynastically",
//            "intersectionality",
//            "exculpatory",
//            "novelistic",
//            "clip",
//            "polemic",
//            "pathos",
//            "aphorism",
//            "vignettes",
//            "gestural",
//            "claque",
//            "carceral")
//    }

}