package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CustomListsGameJoin::class), version = 1)
abstract class BGGDb: RoomDatabase() {
    abstract fun customListGameJoinDao(): CustomListGameJoinDao
}