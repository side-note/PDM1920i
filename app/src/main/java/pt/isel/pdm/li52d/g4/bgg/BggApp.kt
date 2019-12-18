package pt.isel.pdm.li52d.g4.bgg

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import pt.isel.pdm.li52d.g4.bgg.model.BGGDb
import pt.isel.pdm.li52d.g4.bgg.model.BGGRepository
import java.util.concurrent.TimeUnit

const val CHANNEL_ID = "BGG_FAVORITES"

class BggApp : Application(){
    companion object {
        val CUSTOM_LIST_REPO: BGGRepository by lazy { BGGRepository() }
        lateinit var db: BGGDb
        lateinit var bgg: BGGWebApi
    }

    override fun onCreate() {
        super.onCreate()
        bgg = BGGWebApi(applicationContext)
        db = Room
                 .databaseBuilder(applicationContext, BGGDb::class.java, "bgg-db" )
                 .build()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

