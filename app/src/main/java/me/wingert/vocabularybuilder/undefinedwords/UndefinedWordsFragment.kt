package me.wingert.vocabularybuilder.undefinedwords

import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.Toast
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
        adapter = UndefinedWordsAdapter(UndefinedWordsAdapter.OnClickListener { showPopup(it) })

        binding.undefinedWordsList.adapter = adapter
    }

    private fun showPopup(vocab: VocabularyWord) {
        val popupView = layoutInflater.inflate(R.layout.define_word_popup, null)
        val popupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)

        popupView.word_to_define_text.text = vocab.word
        popupView.definition_edit_text.requestFocus()

        popupView.done_button.setOnClickListener { onDoneButtonClicked(vocab, popupView, popupWindow) }
        popupView.delete_icon.setOnClickListener { onDelete(vocab, popupWindow) }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun onDoneButtonClicked(vocab: VocabularyWord, popupView: View, popupWindow: PopupWindow) {
        val definition = popupView.definition_edit_text.text.toString().trim()

        if (definition.isNotEmpty())
            viewModel.addDefinition(vocab, definition)
        else
            Toast.makeText(context, "Definition must not be empty", Toast.LENGTH_SHORT).show()

        popupWindow.dismiss()
    }

    private fun onDelete(vocab: VocabularyWord, popupWindow: PopupWindow) {
        viewModel.delete(vocab)
        popupWindow.dismiss()
    }

}