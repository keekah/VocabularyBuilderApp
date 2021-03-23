package me.wingert.vocabularybuilder.allwords

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.database.DatabaseVocabWord
import me.wingert.vocabularybuilder.database.WordDatabase
import me.wingert.vocabularybuilder.databinding.FragmentAllWordsBinding


class AllWordsFragment : Fragment() {

    private lateinit var binding : FragmentAllWordsBinding
    private lateinit var viewModel : AllWordsViewModel
    private lateinit var viewModelFactory : AllWordsViewModelFactory
    private lateinit var adapter : AllWordsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wordList.observe(viewLifecycleOwner, Observer<List<VocabWord>> {
            words -> words?.apply {
                adapter?.words = words
        }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_words, container, false)

        initializeViewModel()

        initializeAdapter()

        binding.addButton.setOnClickListener { onAdd() }

        binding.wordEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.definitionEdit.visibility = View.VISIBLE
            }
        }

//        viewModel.wordList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

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
    // TODO fix these casts (and their equivalents in the other fragments) -- they break the app, i.e. the click listeners do not work right now
    private fun initializeAdapter() {
        adapter = AllWordsAdapter(AllWordsAdapter.DeleteClickListener { viewModel.deleteWord(it as DatabaseVocabWord) }, AllWordsAdapter.OnClickListener { viewModel.onItemClick(it as DatabaseVocabWord) })

        binding.allWordsList.adapter = adapter
    }

    private fun onAdd() {
        val word = binding.wordEdit.text.toString().trim()
        val definition = binding.definitionEdit.text.toString().trim()

        if (word.isNotEmpty()) {
            viewModel.addWord(word, definition)
        }
        else {
            Toast.makeText(context, "Word must not be empty.", Toast.LENGTH_SHORT).show()
        }

        binding.wordEdit.clearFocus()
        binding.wordEdit.text.clear()
        binding.definitionEdit.text.clear()
        binding.definitionEdit.visibility = View.GONE
        hideKeyboard(binding.wordEdit)
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}