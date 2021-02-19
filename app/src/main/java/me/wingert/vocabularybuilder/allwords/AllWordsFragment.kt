package me.wingert.vocabularybuilder.allwords

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
import me.wingert.vocabularybuilder.databinding.FragmentAllWordsBinding

class AllWordsFragment : Fragment() {

    private lateinit var binding : FragmentAllWordsBinding
    private lateinit var viewModel : AllWordsViewModel
    private lateinit var viewModelFactory : AllWordsViewModelFactory
    private lateinit var adapter : AllWordsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_words, container, false)

        initializeViewModel()

        initializeAdapter()

        setAddButtonClickListener()

        return binding.root
    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = WordDatabase.getInstance(application).wordDao

        viewModelFactory = AllWordsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AllWordsViewModel::class.java)

        binding.viewModel = viewModel
    }


    // TODO fix this OnClickListener. Do I need it? I don't think I do.
    private fun initializeAdapter() {
        adapter = AllWordsAdapter(AllWordsAdapter.DeleteClickListener { viewModel.deleteWord(it) }, AllWordsAdapter.OnClickListener { viewModel.onItemClick(it) })

        binding.allWordsList.adapter = adapter
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