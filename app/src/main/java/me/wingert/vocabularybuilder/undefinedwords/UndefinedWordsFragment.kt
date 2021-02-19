package me.wingert.vocabularybuilder.undefinedwords

import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.define_word_popup.view.*
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.database.VocabularyWord
import me.wingert.vocabularybuilder.database.WordDatabase
import me.wingert.vocabularybuilder.databinding.FragmentUndefinedWordsBinding

class UndefinedWordsFragment : Fragment() {

    private lateinit var binding : FragmentUndefinedWordsBinding
    private lateinit var viewModel: UndefinedWordsViewModel
    private lateinit var viewModelFactory: UndefinedWordsViewModelFactory
    private lateinit var adapter : UndefinedWordsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_undefined_words, container, false)

        initializeViewModel()

        initializeAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.undefinedWords.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.undefinedWords = it
            }
        })

        return binding.root

    }

    private fun initializeViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = WordDatabase.getInstance(application).wordDao

        viewModelFactory = UndefinedWordsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UndefinedWordsViewModel::class.java)

        binding.viewModel = viewModel
    }

    private fun initializeAdapter() {
        adapter = UndefinedWordsAdapter(UndefinedWordsAdapter.OnClickListener { onUndefinedWordClicked(it) })

        binding.undefinedWordsList.adapter = adapter
    }

    private fun onUndefinedWordClicked(vocab: VocabularyWord) {
        val popupView = layoutInflater.inflate(R.layout.define_word_popup, null)
        val popupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupView.word_to_define_text.text = vocab.word
        popupView.definition_edit_text.requestFocus()

        // For some reason adding .toString() to the following line doesn't work, but adding it to the parameter
        // in the line below does work.
        val definition = popupView.definition_edit_text.text
        popupView.done_button.setOnClickListener { onDoneButtonClicked(vocab, definition.toString(), popupWindow) }
        popupView.delete_icon.setOnClickListener { onDelete(vocab, popupWindow) }
    }

    private fun onDoneButtonClicked(vocab: VocabularyWord, definition: String, popupWindow: PopupWindow) {
        viewModel.addDefinition(vocab, definition)
        popupWindow.dismiss()
    }

    private fun onDelete(vocab: VocabularyWord, popupWindow: PopupWindow) {
        viewModel.delete(vocab)
        popupWindow.dismiss()
    }

}