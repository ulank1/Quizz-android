package kg.mvvmdordoi.fcm

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.main.MainActivity
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class FMService : FirebaseMessagingService() {
    private val CHANNEL_ID = "org.sunrise.doc"


    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
//        Log.e("NOtif","dsf"+p0.notification.toString())

        if (p0 != null) {

            var intent = p0.toIntent()
            if (p0.data != null) {

                try {
                    val data = p0.data
                    var type = "0"
                    Log.e("Notif", "" + p0.data)


                    if (p0.notification != null) {
                        Log.e("NO", p0.notification.toString())
                    }
                    data.keys.forEach { Log.e("KEYS", it) }
                    if (data.containsKey("data")) {
                        var json = JSONObject(data["data"])

                        Log.e("Json", json.toString())
                        if (json.has("type")) {
                            type = json.getString("type")
                        }

                        Log.e("Notif", "" + intent)
                        sendNotification(json.getString("body"), json.getString("title"))
                    }else{
                        sendNotification(data["body"]!!, data["title"]!!)

                    }
                }catch (e:Exception){

                }
            } else {
                Log.e("Notif", p0.notification!!.toString())
                //sendNotification(p0.notification!!.body!!, p0.notification!!.title!!)
            }

        }
    }

    private fun sendNotification(message: String,title:String) {

        var resultIntent=Intent(this, MainActivity::class.java)
        if (title.contains("Дуэль")){
            resultIntent.putExtra("is_duel",true)
        }

        resultIntent.action = System.currentTimeMillis().toString()

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val random = Random()
        val notificationId = random.nextInt(9999 - 1000) + 1000

        var i: Int

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            i = NotificationManager.IMPORTANCE_HIGH
        } else {
            i = NotificationCompat.PRIORITY_MAX
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, "All Discount notifications", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(i)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)

        val icon = BitmapFactory.decodeResource(resources, R.drawable.synakprofile)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.synakprofile)
            builder.setLargeIcon(icon)
        } else {
            builder.setLargeIcon(icon)
            builder.setSmallIcon(R.drawable.synakprofile)
        }
        val notification = builder.build()

        notificationManager.notify(notificationId, notification)
    }

}