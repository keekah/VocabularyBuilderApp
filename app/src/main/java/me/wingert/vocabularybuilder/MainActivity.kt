package me.wingert.vocabularybuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.wingert.vocabularybuilder.undefinedwords.UndefinedWordsFragment
import me.wingert.vocabularybuilder.wordlist.DefinedWordsFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val allWordsFragment = DefinedWordsFragment()
        val undefinedWordsFragment = UndefinedWordsFragment()

        setCurrentFragment(allWordsFragment)

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.all_words_tab -> {
                    setCurrentFragment(allWordsFragment)
                    true
                }
                R.id.undefined_words_tab -> {
                    setCurrentFragment(undefinedWordsFragment)
                    Log.i("MainActivity", "Undefined words tab selected")
                    true
                }
                else -> {
                    Log.i("MainActivity", "Other tab selected")
                    true
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }

}