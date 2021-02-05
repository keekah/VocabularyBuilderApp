package me.wingert.vocabularybuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import me.wingert.vocabularybuilder.databinding.FragmentWordListBinding

class WordListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentWordListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)

        return binding.root
    }


}