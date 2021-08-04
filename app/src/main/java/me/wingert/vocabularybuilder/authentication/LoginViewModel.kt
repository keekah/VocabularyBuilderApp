package me.wingert.vocabularybuilder.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState : LiveData<AuthenticationState> = Transformations.map (
        FirebaseUserLiveData()
    ) { user  ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        }
        else
            AuthenticationState.UNAUTHENTICATED
    }

}