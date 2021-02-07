package me.wingert.vocabularybuilder

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {

    private val _wordList = MutableLiveData<List<String>>()
    val wordList : LiveData<List<String>>
        get() = _wordList

    private val _newWord = MutableLiveData<String>()
    val newWord : LiveData<String>
        get() = _newWord


    init {
        populateWordList()
        Log.i("WordListViewModel.kt", "_newWord . value = ${_newWord.value.toString()}")
    }


    fun onAdd(newWord: Editable) {
        // check if word is already in list
        // if not, add the word
        // update
        Log.i("WordListViewModel", "$newWord added to list")
        _wordList.value = _wordList.value?.plus(newWord.toString())
    }

    fun finishedAdding() {
        _newWord.value = ""
    }

    private fun populateWordList() {
        _wordList.value = mutableListOf(
            "hirsute",
            "milieu",
            "halcyon",
            "screed",
            "excoriate",
            "lambaste",
            "poky",
            "loosies",
            "nascent",
            "reticent",
            "curative",
            "akimbo",
            "artifice",
            "panniers",
            "necropolitics",
            "cadre",
            "posterity",
            "metathesis",
            "sacrosanct",
            "maieutic",
            "convocation",
            "boughs",
            "canonically",
            "intimated",
            "commensurate",
            "thrall",
            "enervation",
            "stanch",
            "aquiline",
            "rapacious",
            "brogues",
            "pied-à-terre ",
            "replete",
            "credenza",
            "requisite",
            "extolling",
            "simulacrum",
            "meritocracy",
            "entail",
            "pernicious",
            "perfunctorily",
            "paragon",
            "ethos",
            "banal",
            "yoked",
            "aviophobe",
            "reactionary",
            "ad hoc ",
            "cohort",
            "fraught",
            "staid",
            "nootropic",
            "fatuous",
            "scrim",
            "dynastically",
            "intersectionality",
            "exculpatory",
            "novelistic",
            "clip",
            "polemic",
            "pathos",
            "aphorism",
            "vignettes",
            "gestural",
            "claque",
            "carceral")
    }

}