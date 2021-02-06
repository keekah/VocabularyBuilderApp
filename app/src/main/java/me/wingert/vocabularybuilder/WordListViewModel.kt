package me.wingert.vocabularybuilder

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {

    private val _wordList = mutableListOf<LiveData<String>>()
    val wordList : List<LiveData<String>>
        get() = _wordList

    private var _newWord = MutableLiveData<String?>()
    val newWord : LiveData<String?>
        get() = _newWord


    init {
        populateWordList()
    }

    fun getNewWord(word: Editable) {
        _newWord.value = word.toString()
    }

    fun onAdd(newWord: Editable) {
        // check if word is already in list
        // if not, add the word
        // update
        _wordList.add(MutableLiveData(newWord.toString()))
        Log.i("WordListViewModel", "$newWord added to list")
    }

    private fun populateWordList() {
        _wordList.add(MutableLiveData("hirsute"))
        _wordList.add(MutableLiveData("milieu"))
        _wordList.add(MutableLiveData("halcyon"))
        _wordList.add(MutableLiveData("screed"))
        _wordList.add(MutableLiveData("excoriate"))
        _wordList.add(MutableLiveData("lambaste"))
        _wordList.add(MutableLiveData("poky"))
        _wordList.add(MutableLiveData("loosies"))
        _wordList.add(MutableLiveData("nascent"))
        _wordList.add(MutableLiveData("reticent"))
        _wordList.add(MutableLiveData("curative"))
        _wordList.add(MutableLiveData("akimbo"))
        _wordList.add(MutableLiveData("artifice"))
        _wordList.add(MutableLiveData("panniers"))
        _wordList.add(MutableLiveData("necropolitics"))
        _wordList.add(MutableLiveData("cadre"))
        _wordList.add(MutableLiveData("posterity"))
        _wordList.add(MutableLiveData("metathesis"))
        _wordList.add(MutableLiveData("sacrosanct"))
        _wordList.add(MutableLiveData("maieutic"))
        _wordList.add(MutableLiveData("convocation"))
        _wordList.add(MutableLiveData("boughs"))
        _wordList.add(MutableLiveData("canonically"))
        _wordList.add(MutableLiveData("intimated"))
        _wordList.add(MutableLiveData("commensurate"))
        _wordList.add(MutableLiveData("thrall"))
        _wordList.add(MutableLiveData("enervation"))
        _wordList.add(MutableLiveData("stanch"))
        _wordList.add(MutableLiveData("aquiline"))
        _wordList.add(MutableLiveData("rapacious"))
        _wordList.add(MutableLiveData("brogues"))
        _wordList.add(MutableLiveData("pied-à-terre "))
        _wordList.add(MutableLiveData("replete"))
        _wordList.add(MutableLiveData("credenza"))
        _wordList.add(MutableLiveData("requisite"))
        _wordList.add(MutableLiveData("extolling"))
        _wordList.add(MutableLiveData("simulacrum"))
        _wordList.add(MutableLiveData("meritocracy"))
        _wordList.add(MutableLiveData("entail"))
        _wordList.add(MutableLiveData("pernicious"))
        _wordList.add(MutableLiveData("perfunctorily"))
        _wordList.add(MutableLiveData("paragon"))
        _wordList.add(MutableLiveData("ethos"))
        _wordList.add(MutableLiveData("banal"))
        _wordList.add(MutableLiveData("yoked"))
        _wordList.add(MutableLiveData("aviophobe"))
        _wordList.add(MutableLiveData("reactionary"))
        _wordList.add(MutableLiveData("ad hoc "))
        _wordList.add(MutableLiveData("cohort"))
        _wordList.add(MutableLiveData("fraught"))
        _wordList.add(MutableLiveData("staid"))
        _wordList.add(MutableLiveData("nootropic"))
        _wordList.add(MutableLiveData("fatuous"))
        _wordList.add(MutableLiveData("scrim"))
        _wordList.add(MutableLiveData("dynastically"))
        _wordList.add(MutableLiveData("intersectionality"))
        _wordList.add(MutableLiveData("exculpatory"))
        _wordList.add(MutableLiveData("novelistic"))
        _wordList.add(MutableLiveData("clip"))
        _wordList.add(MutableLiveData("polemic"))
        _wordList.add(MutableLiveData("pathos"))
        _wordList.add(MutableLiveData("aphorism"))
        _wordList.add(MutableLiveData("vignettes"))
        _wordList.add(MutableLiveData("gestural"))
        _wordList.add(MutableLiveData("claque"))
        _wordList.add(MutableLiveData("carceral"))
    }

}