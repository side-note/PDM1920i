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

    @Query("DELETE FROM games WHERE nameFavListGame LIKE :nameFavList")
    fun deleteFavGame(nameFavList: String)

    @Query("DELETE FROM favorites WHERE nameFavList LIKE :nameFavList")
    fun deleteFavList(nameFavList: String)

    @Query("DELETE FROM designer WHERE gameName LIKE :gameName")
    fun deleteFavDesigners(gameName: String)

    @Query("DELETE FROM mechanics WHERE nameFavListM LIKE :nameFavList")
    fun deleteMechanics(nameFavList: String)

    @Query("DELETE FROM categories WHERE nameFavListC LIKE :nameFavList")
    fun deleteCategories(nameFavList: String)


}