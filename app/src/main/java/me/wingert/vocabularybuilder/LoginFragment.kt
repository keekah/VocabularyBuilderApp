package me.wingert.vocabularybuilder

import android.app.Activity
import android.content.Intent
import android.media.session.MediaSessionManager
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import me.wingert.vocabularybuilder.allwords.AllWordsFragment
import me.wingert.vocabularybuilder.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.authButton.setOnClickListener { launchSignInFlow() }

        sessionManager = SessionManager(requireContext())
        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            val user = FirebaseAuth.getInstance().currentUser

            if (resultCode == Activity.RESULT_OK) {
                Log.i("LoginFragment", "Successfully signed in user ${user?.displayName}!")
                // Retrieve the JWT used to identify the user to Firebase. true indicates that the
                // token will always be refreshed. OnCompleteListener allows us to handle success
                // and failure in the same listener.
                Log.i("LoginFragment", "Acquiring token...")

                user!!.getIdToken(true)
                    .addOnCompleteListener(object: OnCompleteListener<GetTokenResult> {
                        override fun onComplete(@NonNull task: Task<GetTokenResult>) {
                            Log.i("LoginFragment", "Calling oncompletelistener")
                            if (task.isSuccessful) {
                                val idToken = task.result?.token;
                                Log.i("LoginFragment", "Token acquired! $idToken")
                                sessionManager.saveAuthToken(idToken!!)
//                                 TODO Send token to backend via HTTPS
                                // Navigate
                                activity?.supportFragmentManager?.beginTransaction()?.apply {
                                    replace(R.id.nav_host_fragment, AllWordsFragment())
                                    commit()
                                }

                            } else {
                                Log.d("LoginFragment", "Failed to retrieve JWT. ${task.exception}")
                            }
                        }
                    })

                Log.i("LoginFragment", "outside getId token")



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
                .setTheme(R.style.OrangeTheme)
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }
}