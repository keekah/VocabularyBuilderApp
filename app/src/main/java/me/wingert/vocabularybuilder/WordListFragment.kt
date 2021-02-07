package me.wingert.vocabularybuilder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.databinding.FragmentWordListBinding

class WordListFragment : Fragment() {

    private lateinit var binding : FragmentWordListBinding
    private lateinit var viewModel : WordListViewModel
    private lateinit var viewModelFactory : WordListViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)


        viewModelFactory = WordListViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordListViewModel::class.java)

        binding.viewModel = viewModel


        val adapter = WordAdapter()
        binding.wordList.adapter = adapter

        binding.addButton.setOnClickListener { viewModel.onAdd(binding.wordEdit.text) }

        viewModel.wordList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

}