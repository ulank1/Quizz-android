package kg.mvvmdordoi.fcm

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

import java.io.IOException

object FCMTokenUtils {


    fun deleteToken(context: Context) {
        try {
            val originalToken = getTokenFromPrefs(context)
            Log.d("FCMToken", "Token before deletion: $originalToken")
            FirebaseInstanceId.getInstance().deleteInstanceId()

            // Clear current saved token
            saveTokenToPrefs("", context)
            // Now manually call onTokenRefresh()
            Log.d("FCMToken", "Getting new token")
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
                val newToken = task.result!!.id
                saveTokenToPrefs(newToken, context)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun saveTokenToPrefs(_token: String, context: Context) {
        // Access Shared Preferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        // Save to SharedPreferences
        editor.putString("registration_id", _token)
        editor.apply()
    }

    fun getTokenFromPrefs(context: Context): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var regId:String? = preferences.getString("registration_id", null)

        if (regId == null || regId.isEmpty()) {
            regId = FirebaseInstanceId.getInstance().token
            Log.e("REQQDSD",regId.toString())
        }
        return regId.toString()
    }
}
