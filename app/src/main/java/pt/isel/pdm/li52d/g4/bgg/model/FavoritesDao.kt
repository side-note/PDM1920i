package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMechanics(mechanics: Array<out Mechanics>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: Array<out Categories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Array<out Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: Favorites)

    @Query("SELECT * FROM mechanics WHERE nameFavListM LIKE :nameFavList")
    fun getMechanicsForFavorites(nameFavList: String): List<Mechanics>

    @Query("SELECT * FROM categories WHERE nameFavListC LIKE :nameFavList")
    fun getCategoriesForFavorites(nameFavList: String): List<Categories>

    @Query("SELECT * FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun getGamesForFavorites(nameFavList: String): List<Game>


}