package pt.isel.pdm.li52d.g4.bgg

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import pt.isel.pdm.li52d.g4.bgg.view.GameListActivity
import java.util.concurrent.CompletableFuture

const val NOTIFICATION_ID = 100012

class WorkerFavorites(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val cf = CompletableFuture<GameDto>()
        Log.v(TAG, "Get TopChart in background...")
//TODO:
//        BggApp.bgg.favoriteSearch()
        return try {
            val dto: GameDto = cf.get()
            Log.v(TAG, "Get TopChart COMPLETED")
//            val arr = dtoToModel(dto)
//            GeniuzApp.db.topchartDao().insertAll(*arr)
            /**
             * Check if there is any modification on Top Chart and
             * notify the Channel in that case TO DO !!!!
             */

            notifyFavoritesChannel()
            Result.success()
        } catch (e: Exception) {
            Log.v(TAG, "Get Favorites List FAILED")
            Result.failure()
            throw e
        }
    }

    private fun notifyFavoritesChannel() {
        val intent = Intent(applicationContext, GameListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Favorites")
            .setContentText("Update on Favorites List")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat
            .from(applicationContext)
            .notify(NOTIFICATION_ID, notification)
    }
}