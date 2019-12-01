package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.*

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDesigner(designer : Designer)

    @Query("SELECT * FROM mechanics WHERE nameFavListM LIKE :nameFavList")
    fun getMechanicsForFavorites(nameFavList: String): List<Mechanics>

    @Query("SELECT * FROM categories WHERE nameFavListC LIKE :nameFavList")
    fun getCategoriesForFavorites(nameFavList: String): List<Categories>

    @Query("SELECT * FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun getGamesForFavorites(nameFavList: String): List<DesignersAndGames>

    @Query("SELECT * FROM favorites WHERE nameFavList LIKE :nameFavList")
    fun getFavList(nameFavList: String): Favorites

    @Query("SELECT * FROM favorites")
    fun getAllFavs(): List<Favorites>

    @Delete
    fun deleteFavGame(game: Game)

    @Delete
    fun deleteFavList(favorites: Favorites)
    @Delete
    fun deleteFavDesigners(designer: Designer)

}