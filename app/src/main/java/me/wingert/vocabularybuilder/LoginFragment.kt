package me.wingert.vocabularybuilder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import me.wingert.vocabularybuilder.allwords.AllWordsFragment
import me.wingert.vocabularybuilder.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.authButton.setOnClickListener { launchSignInFlow() }

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            val user = FirebaseAuth.getInstance().currentUser

            if (resultCode == Activity.RESULT_OK) {
                Log.i("LoginFragment", "Successfully signed in user ${user?.displayName}!")
                // Navigate
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.nav_host_fragment, AllWordsFragment())
                    commit()
                }

                // Check if user is new or returning
                storeUserIfNew(user)
            }
            else {
                Log.i("LoginFragment", "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun storeUserIfNew(user: FirebaseUser?) {
        val metadata = user?.metadata
        if (metadata?.creationTimestamp == metadata?.lastSignInTimestamp) {
            // New user, add them to the users table
            // TODO
        }
    }

    private fun launchSignInFlow() {

        Log.i("LoginFragment", "LoginButton clicked")

        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build(),
                                    AuthUI.IdpConfig.GoogleBuilder().build()
                                   )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }
}