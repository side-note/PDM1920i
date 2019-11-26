package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.*

@Dao
interface BGGDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game : Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list : CustomList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist:Artist)

    // @Query("SELECT * FROM CustomLists where name = :customlistName")
    //fun getGamesForCustomList(customlistName : String): LiveData<List<CustomListsAndGames>>
    @Delete
    fun delete(game: Game)
}