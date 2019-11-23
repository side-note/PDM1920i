package pt.isel.pdm.li52d.g4.bgg.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomListGameJoinDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg customlistGamesJoin : CustomListsGamesJoin)

    @Query("""
               SELECT * FROM games
               INNER JOIN customlist_games_join
               ON games.id=customlist_games_join.gameId
               WHERE customlist_games_join.customlistId=:customlistName
               """)
    fun getGamesForCustomList(customlistName : String): Array<Games>

    @Delete
    fun delete(game: Games)
}