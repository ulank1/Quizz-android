package kg.mvvmdordoi.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

private val TAG = Lang::class.java.simpleName
object Lang{
    val APP_PREFERENCES = "Lang"
    val TOKEN = "lang_set"


    private var mSettings: SharedPreferences? = null

    fun save(token: String, context: Context) {

        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val editor = mSettings!!.edit()
        editor.putString(TOKEN, token)
        Log.e(TAG, "saved $token")
        editor.apply()
    }

    fun get(context: Context): String? {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return mSettings!!.getString(TOKEN, "1")
    }

    fun clear(context: Context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = mSettings!!.edit().clear()
        editor.apply()
    }

    fun has(context: Context): Boolean {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val token = mSettings!!.getString(TOKEN, "empty")
        Log.e(TAG, token)
        return token != "empty" && token != ""
    }




}