package pt.isel.pdm.li52d.g4.bgg.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMechanics(mechanics: Mechanics)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: Categories)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: Favorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDesigner(designer : Designer)

    @Query("SELECT * FROM mechanics WHERE nameFavListM LIKE :nameFavList")
    fun getMechanicsForFavorites(nameFavList: String): List<Mechanics>

    @Query("SELECT * FROM categories WHERE nameFavListC LIKE :nameFavList")
    fun getCategoriesForFavorites(nameFavList: String): List<Categories>

    @Query("SELECT * FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun getGamesForFavorites(nameFavList: String): LiveData<Array<DesignersAndGames>>

    @Query("SELECT * FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun getGamesForFavoritesSync(nameFavList: String): List<DesignersAndGames>

    @Query("SELECT * FROM favorites WHERE nameFavList LIKE :nameFavList")
    fun getFavList(nameFavList: String): Favorites

    @Query("SELECT * FROM favorites")
    fun getAllFavs(): List<Favorites>

    @Query("DELETE FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun deleteFavGame(nameFavList: String)

    @Query("DELETE FROM favorites WHERE nameFavList LIKE :nameFavList")
    fun deleteFavList(nameFavList: String)

    @Query("DELETE FROM designer WHERE gameName LIKE :gameName AND listName LIKE :listName")
    fun deleteFavDesigners(gameName: String, listName: String)

    @Query("DELETE FROM mechanics WHERE nameFavListM LIKE :nameFavList")
    fun deleteMechanics(nameFavList: String)

    @Query("DELETE FROM categories WHERE nameFavListC LIKE :nameFavList")
    fun deleteCategories(nameFavList: String)

    @Query("UPDATE favorites SET id = :id WHERE nameFavList = :favListName")
    fun updateWorkerId(favListName: String, id: String)
}