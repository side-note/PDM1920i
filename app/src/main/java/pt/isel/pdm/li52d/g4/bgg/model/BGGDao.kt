package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.*

@Dao
interface BGGDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game : Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list : CustomList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDesigner(designer : Designer)

    @Query("SELECT * FROM games WHERE nameList LIKE :customlistName")
    fun getGamesForCustomList(customlistName : String): List<DesignersAndGames>

    @Query("SELECT * FROM lists")
    fun getAllCustomList(): List<CustomList>

    @Query("SELECT * FROM lists WHERE name LIKE :listName")
    fun getCustomList(listName: String): CustomList

    @Delete
    fun delete(game: Game)

    @Delete
    fun deleteList(customList: CustomList)
    @Delete
    fun deleteDesigners(designer: Designer)

}