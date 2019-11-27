package pt.isel.pdm.li52d.g4.bgg.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BGGDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game : Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list : CustomList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist:Artist)

    @Query("SELECT * FROM games WHERE nameList LIKE :customlistName")
    fun getGamesForCustomList(customlistName : String): List<ArtistsAndGames>

    @Query("SELECT * FROM lists")
    fun getCustomList(): List<CustomList>
//    @Query("SELECT * FROM CustomListsAndGames where list = :list")
//    fun getGamesForCustomListAndGames(list : CustomList): LiveData<List<CustomListsAndGames>>

    @Delete
    fun delete(game: Game)


}