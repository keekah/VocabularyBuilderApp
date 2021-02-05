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
import androidx.lifecycle.ViewModelProvider
import me.wingert.vocabularybuilder.databinding.FragmentWordListBinding

class WordListFragment : Fragment() {

    private lateinit var viewModel : WordListViewModel
    private lateinit var viewModelFactory : WordListViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentWordListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)

        viewModelFactory = WordListViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordListViewModel::class.java)

        binding.viewModel = viewModel

        val layout = binding.scrollLayout

        if (this::viewModel.isInitialized)
            createTextViews(layout)
        else
            Log.i("WordListFragment", "ViewModel not initialized")

        return binding.root
    }

    private fun createTextViews(layout : LinearLayout) {
        Log.i("WordListFragment", "list size: ${viewModel.wordList.size}")
        for (word in viewModel.wordList) {
            val newTextView = TextView(context)
            newTextView.text = word.value
            newTextView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layout.addView(newTextView)
        }
    }


}