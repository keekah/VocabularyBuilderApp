package me.wingert.vocabularybuilder.definedwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.allwords.AllWordsAdapter
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import me.wingert.vocabularybuilder.databinding.FragmentDefinedWordsBinding

class DefinedWordsFragment : Fragment() {

    private lateinit var binding : FragmentDefinedWordsBinding
    private lateinit var viewModel: DefinedWordsViewModel
    private lateinit var viewModelFactory: DefinedWordsViewModelFactory
    private lateinit var adapter : AllWordsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_defined_words, container, false)

        initializeViewModel()

        initializeAdapter()

        viewModel.wordListDefined.observe(viewLifecycleOwner, Observer<List<VocabWord>> { words ->
            words?.apply {
                adapter.words = words
                setHeader(words.isNullOrEmpty())
            }
        })

        return binding.root
    }

    private fun initializeViewModel() {
        val application = requireNotNull(activity).application
        val dataSource = VocabularyBuilderDB.getInstance(application).wordDao
        viewModelFactory = DefinedWordsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DefinedWordsViewModel::class.java)
    }

    private fun initializeAdapter() {
        adapter = AllWordsAdapter(AllWordsAdapter.DeleteClickListener { viewModel.deleteWord(it) })
        binding.definedWordsList.adapter = adapter
    }

    private fun setHeader(isNullOrEmpty: Boolean) {
        binding.definedWordsEmptyText.visibility = when (isNullOrEmpty) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }
}