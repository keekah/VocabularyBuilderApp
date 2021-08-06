package me.wingert.vocabularybuilder.allwords

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import me.wingert.vocabularybuilder.databinding.FragmentAllWordsBinding


class AllWordsFragment : Fragment() {

    private lateinit var binding : FragmentAllWordsBinding
    private lateinit var viewModel : AllWordsViewModel
    private lateinit var viewModelFactory : AllWordsViewModelFactory
    private lateinit var adapter : AllWordsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        viewModel.wordListAll.observe(viewLifecycleOwner, Observer<List<VocabWord>> {
            words -> words?.apply {
                adapter.words = words
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_item) {
            AuthUI.getInstance().signOut(requireContext())
        }

        return super.onOptionsItemSelected(item)
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

        return binding.root
    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = VocabularyBuilderDB.getInstance(application).wordDao

        viewModelFactory = AllWordsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AllWordsViewModel::class.java)

        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = AllWordsAdapter(AllWordsAdapter.DeleteClickListener { viewModel.deleteWord(it) })

        binding.allWordsList.adapter = adapter
    }

    private fun onAdd() {
        val locale = ConfigurationCompat.getLocales(resources.configuration)[0]
        val word = binding.wordEdit.text.toString().trim().toLowerCase(locale)
        val definition = binding.definitionEdit.text.toString().trim().toLowerCase(locale)

        if (word.isNotEmpty()) {
            // Check for Noorvik Easter Egg
            if (word == "99763") {
                viewModel.cheerForBears(word, definition)
            }
            else
                viewModel.addWord(word, definition)
        }
        else {
            Toast.makeText(context, "Word must not be empty", Toast.LENGTH_SHORT).show()
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