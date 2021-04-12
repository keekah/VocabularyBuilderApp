package me.wingert.vocabularybuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.wingert.vocabularybuilder.undefinedwords.UndefinedWordsFragment
import me.wingert.vocabularybuilder.allwords.AllWordsFragment
import me.wingert.vocabularybuilder.definedwords.DefinedWordsFragment

class MainActivity : AppCompatActivity() {

    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)

        viewModel.authenticationState.observe(this, Observer { authState ->
            bottomNavigationView.visibility = when (authState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> View.VISIBLE
                else -> View.INVISIBLE
            }
            if (authState == LoginViewModel.AuthenticationState.UNAUTHENTICATED)
                setCurrentFragment(LoginFragment())
        })

        val allWordsFragment = AllWordsFragment()
        val definedWordsFragment = DefinedWordsFragment()
        val undefinedWordsFragment = UndefinedWordsFragment()


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
                    setCurrentFragment(definedWordsFragment)
                    Log.i("MainActivity", "Other tab selected")
                    true
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }
    }

}