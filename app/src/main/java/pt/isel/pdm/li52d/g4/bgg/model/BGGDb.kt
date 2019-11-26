package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Game::class, CustomList::class, Artist::class], version = 2)
abstract class BGGDb: RoomDatabase() {
    abstract fun customListAndGamesDao(): BGGDao
}