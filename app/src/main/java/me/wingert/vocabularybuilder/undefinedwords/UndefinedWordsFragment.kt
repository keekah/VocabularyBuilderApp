package me.wingert.vocabularybuilder.undefinedwords

import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.VocabWord
import me.wingert.vocabularybuilder.databinding.DefineWordPopupBinding
import me.wingert.vocabularybuilder.room.VocabularyBuilderDB
import me.wingert.vocabularybuilder.databinding.FragmentUndefinedWordsBinding

class UndefinedWordsFragment : Fragment() {

    private lateinit var binding : FragmentUndefinedWordsBinding
    private lateinit var popupBinding: DefineWordPopupBinding
    private lateinit var viewModel: UndefinedWordsViewModel
    private lateinit var viewModelFactory: UndefinedWordsViewModelFactory
    private lateinit var adapter : UndefinedWordsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentUndefinedWordsBinding.inflate(inflater, container, false)
        popupBinding = DefineWordPopupBinding.inflate(inflater, container, false)

        initializeViewModel()

        initializeAdapter()


        viewModel.undefinedWords.observe(viewLifecycleOwner, Observer<List<VocabWord>> { words ->
            words?.apply {
                adapter.undefinedWords = words
                setHeader(words.isNullOrEmpty())
            }
        })

        return binding.root

    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = VocabularyBuilderDB.getInstance(application).wordDao

        viewModelFactory = UndefinedWordsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UndefinedWordsViewModel::class.java)

        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = UndefinedWordsAdapter(UndefinedWordsAdapter.OnClickListener { showPopup(it) })

        binding.undefinedWordsList.adapter = adapter
    }

    private fun showPopup(vocab: VocabWord) {
        val popupView = popupBinding.popupWindow
        val popupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)

        popupBinding.wordToDefineText.text = vocab.word
        popupBinding.definitionEditText.requestFocus()

        popupBinding.doneButton.setOnClickListener{ onDoneButtonClicked(vocab, popupView, popupWindow) }
        popupBinding.deleteIcon.setOnClickListener { onDelete(vocab, popupWindow) }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun onDoneButtonClicked(vocabWord: VocabWord, popupView: View, popupWindow: PopupWindow) {
        val definition = popupBinding.definitionEditText.text.toString().trim().toLowerCase()

        if (definition.isNotEmpty())
            viewModel.addDefinition(vocabWord, definition)
        else
            Toast.makeText(context, "Definition must not be empty", Toast.LENGTH_SHORT).show()

        popupWindow.dismiss()
    }

    private fun onDelete(vocabWord: VocabWord, popupWindow: PopupWindow) {
        viewModel.deleteWord(vocabWord)
        popupWindow.dismiss()
    }

    // Set the text based on the list of undefined words.
    private fun setHeader(isNullOrEmpty: Boolean) {
        binding.addDefinitionText.text =
            if (isNullOrEmpty)
                getString(R.string.no_undefined_words)
            else
                getString(R.string.select_to_add_definition)
    }

}