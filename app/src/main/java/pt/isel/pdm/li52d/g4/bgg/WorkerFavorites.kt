package pt.isel.pdm.li52d.g4.bgg

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import pt.isel.pdm.li52d.g4.bgg.dto.GameSearchDto
import pt.isel.pdm.li52d.g4.bgg.model.*
import pt.isel.pdm.li52d.g4.bgg.view.GameListActivity
import java.util.concurrent.CompletableFuture

const val NOTIFICATION_ID = 100012

class WorkerFavorites(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val cf = CompletableFuture<GameSearchDto>()
        Log.v(TAG, "Get Favorites in background...")
        BggApp.bgg.favoriteSearch(
            inputData.getString(PUBLISHER_NAME)!!,
            inputData.getString(DESIGNER_NAME)!!,
            inputData.getString(MECHANICS_URL)!!,
            inputData.getString(CATEGORIES_URL)!!,
            {cf.complete(it)},
            {cf.completeExceptionally(it)}
        )
        return try {
            val listName = "Fav " + inputData.getString(FAV_LIST_NAME)!!
            val oldGames = BggApp.db.FavoritesDao().getGamesForFavorites(listName)
            val dto: GameSearchDto = cf.get()
            Log.v(TAG, "Get Favorites COMPLETED")
            dto.games.forEach {
                val designersAndGames = BggApp.CUSTOM_LIST_REPO.fromGameDto(it)
                designersAndGames.game.nameFavListGame = listName
                BggApp.db.FavoritesDao().insertGame(designersAndGames.game)
            }
            val newGames = BggApp.db.FavoritesDao().getGamesForFavorites(listName)
            /**
             * Check if there is any modification on Top Chart and
             * notify the Channel in that case
             */
            val size = oldGames.value?.size
            if( size != dto.games.size)
                notifyFavoritesChannel()
            else {
                newGames.value?.forEach {
                    if(!oldGames.value?.contains(it)!!)
                        notifyFavoritesChannel()
                }
            }
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
        intent.putExtra(FAVORITE, inputData.getString(FAV_LIST_NAME))
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