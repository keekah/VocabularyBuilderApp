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
import me.wingert.vocabularybuilder.allwords.AllWordsAdapter
import me.wingert.vocabularybuilder.database.WordDatabase
import me.wingert.vocabularybuilder.databinding.FragmentDefinedWordsBinding

class DefinedWordsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentDefinedWordsBinding>(inflater, R.layout.fragment_defined_words, container, false)

        val application = requireNotNull(activity).application
        val dataSource = WordDatabase.getInstance(application).wordDao
        val viewModelFactory = DefinedWordsViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DefinedWordsViewModel::class.java)

        val adapter = AllWordsAdapter(AllWordsAdapter.DeleteClickListener { viewModel.deleteWord(it) }, AllWordsAdapter.OnClickListener { viewModel.onItemClick(it) })
        binding.definedWordsList.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.definedWords.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })



        return binding.root
    }
}