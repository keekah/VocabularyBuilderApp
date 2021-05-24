package me.wingert.vocabularybuilder

import android.content.Context

// Used to save and retrieve the token on the user's device
class SessionManager(context: Context) {

    private var sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken() : String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

}