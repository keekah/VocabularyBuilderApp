package me.wingert.vocabularybuilder.wordlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.database.WordDatabase
import me.wingert.vocabularybuilder.databinding.FragmentWordListBinding

class DefinedWordsFragment : Fragment() {

    private lateinit var binding : FragmentWordListBinding
    private lateinit var viewModel : DefinedWordsViewModel
    private lateinit var viewModelFactory : DefinedWordsViewModelFactory
    private lateinit var adapter : DefinedWordsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)

        initializeViewModel()

        initializeAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        setAddButtonClickListener()

        return binding.root
    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = WordDatabase.getInstance(application).wordDao

        viewModelFactory = DefinedWordsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DefinedWordsViewModel::class.java)

        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = DefinedWordsAdapter(DefinedWordsAdapter.DeleteClickListener { viewModel.deleteWord(it) }, DefinedWordsAdapter.OnClickListener { viewModel.onItemClick(it) })

        binding.wordList.adapter = adapter
    }

    private fun setAddButtonClickListener() {
        binding.addButton.setOnClickListener { addWord() }

        viewModel.wordList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
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