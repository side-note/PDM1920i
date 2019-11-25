package pt.isel.pdm.li52d.g4.bgg.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomListGameJoinDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customListsGameJoin : CustomListsGameJoin)

    @Query("""
               SELECT * FROM game
               INNER JOIN customlist_game_join
               ON game.id=customlist_game_join.gameId
               WHERE customlist_game_join.customlistName=:customlistName
               """)
    fun getGamesForCustomList(customlistName : String): LiveData<Game>

    @Delete
    fun delete(game: Game)
}