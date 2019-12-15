package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import java.util.concurrent.CompletableFuture

class WorkerFavorites(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val cf = CompletableFuture<GameDto>()
        Log.v(TAG, "Get TopChart in background...")
        BggApp.bgg.favoriteSearch()
        GeniuzApp.lastfm.favorites({
            cf.complete(it)
        }, {
            cf.completeExceptionally(it)
        })
        return try {
            val dto: GameDto = cf.get()
            Log.v(TAG, "Get TopChart COMPLETED")
            val arr = dtoToModel(dto)
            GeniuzApp.db.topchartDao().insertAll(*arr)
            /**
             * Check if there is any modification on Top Chart and
             * notify the Channel in that case TO DO !!!!
             */
            notifyTopChartChannel()
            Result.success()
        } catch (e: Exception) {
            Log.v(TAG, "Get TopChart FAILED")
            Result.failure()
            throw e
        }
    }
}