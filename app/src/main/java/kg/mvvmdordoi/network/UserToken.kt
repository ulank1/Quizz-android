package kg.mvvmdordoi.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

private val TAG = UserToken::class.java.simpleName
object UserToken{
    val APP_PREFERENCES = "mysettings"
    val TOKEN = "tokenID"
    val LANG = "tokenID"


    private var mSettings: SharedPreferences? = null

    fun saveToken(token: String, context: Context) {

        UserToken.mSettings = context.getSharedPreferences(UserToken.APP_PREFERENCES, Context.MODE_PRIVATE)

        val editor = UserToken.mSettings!!.edit()
        editor.putString(TOKEN, token)
        Log.e(TAG, "saved $token")
        editor.apply()
    }

    fun getToken(context: Context): String? {
        UserToken.mSettings = context.getSharedPreferences(UserToken.APP_PREFERENCES, Context.MODE_PRIVATE)
        return UserToken.mSettings!!.getString(TOKEN, "empty")
    }

    fun clearToken(context: Context) {
        UserToken.mSettings = context.getSharedPreferences(UserToken.APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = UserToken.mSettings!!.edit().clear()
        editor.apply()
    }

    fun hasToken(context: Context): Boolean {
        UserToken.mSettings = context.getSharedPreferences(UserToken.APP_PREFERENCES, Context.MODE_PRIVATE)
        val token = UserToken.mSettings!!.getString(TOKEN, "empty")
        Log.e(TAG, token)
        return token != "empty" && token != ""
    }




}