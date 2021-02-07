package me.wingert.vocabularybuilder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.databinding.FragmentWordListBinding

class WordListFragment : Fragment() {

    private lateinit var binding : FragmentWordListBinding
    private lateinit var viewModel : WordListViewModel
    private lateinit var viewModelFactory : WordListViewModelFactory
    private lateinit var adapter : WordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)

        initializeViewModel()

        initializeAdapter()

        setAddButtonClickListener()

        return binding.root
    }

    private fun initializeViewModel() {
        viewModelFactory = WordListViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordListViewModel::class.java)

        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = WordAdapter()

        binding.wordList.adapter = adapter
    }

    private fun setAddButtonClickListener() {
        binding.addButton.setOnClickListener { addWord() }

        viewModel.wordList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
                viewModel.finishedAdding()
            }
        })
    }

    private fun addWord() {
        viewModel.onAdd(binding.wordEdit.text)
        binding.wordEdit.text.clear()
        hideKeyboard(binding.wordEdit)
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}