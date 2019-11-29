package pt.isel.pdm.li52d.g4.bgg.model

import android.os.AsyncTask
import android.util.Log
import com.android.volley.VolleyError
import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.TAG
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto

class BGGRepository {
    private class insertListTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg nameList: String) {
            BggApp.db.customListAndGamesDao().insertList(CustomList(nameList[0]))
        }
    }

    private class insertGameinListTask : AsyncTask<Game, Unit, Unit>() {
        override fun doInBackground(vararg game: Game) {
            BggApp.db.customListAndGamesDao().insertGame(game[0])
        }
    }

    private class insertArtistTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.customListAndGamesDao().insertArtist(Artist(params[0], params[1]))
        }
    }

    private class insertFavoritesTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.FavoritesDao().insertFavorites(Favorites(params[0], params[1], params[2]))
        }
    }

    private class insertGamesinFavoritesTask : AsyncTask<Array<out Game>, Unit, Unit>() {
        override fun doInBackground(vararg game: Array<out Game>) {
            game.forEach { BggApp.db.FavoritesDao().insertGame(it) }
        }
    }

    private class insertMechanicsinFavoritesTask : AsyncTask<Array<out Mechanics>, Unit, Unit>() {
        override fun doInBackground(vararg mechanics: Array<out Mechanics>) {
            mechanics.forEach { BggApp.db.FavoritesDao().insertMechanics(it) }
        }
    }

    private class insertCategoriesinFavoritesTask : AsyncTask<Array<out Categories>, Unit, Unit>() {
        override fun doInBackground(vararg categories: Array<out Categories>) {
            categories.forEach { BggApp.db.FavoritesDao().insertCategories(it) }
        }
    }


    private class getGamesListTask : AsyncTask<String, Unit, List<ArtistsAndGames>>() {
        override fun doInBackground(vararg name: String): List<ArtistsAndGames> {
            return BggApp.db.customListAndGamesDao().getGamesForCustomList(name[0])
        }
    }
    private class getListTask : AsyncTask<String, Unit, CustomList>() {
        override fun doInBackground(vararg name: String): CustomList {
            return BggApp.db.customListAndGamesDao().getCustomList(name[0])
        }
    }
    private class getAllListTask : AsyncTask<Unit, Unit, List<CustomList>>() {
        override fun doInBackground(vararg dummy : Unit): List<CustomList> {
            return BggApp.db.customListAndGamesDao().getAllCustomList()
        }
    }
    private class deleteGamesinListTask : AsyncTask<Game, Unit, Unit>(){
        override fun doInBackground(vararg games: Game) {
            games.forEach {
                BggApp.db.customListAndGamesDao().delete(it)
            }
        }
    }
    private class deleteListTask : AsyncTask<CustomList, Unit, Unit>(){
        override fun doInBackground(vararg lists: CustomList) {
            BggApp.db.customListAndGamesDao().deleteList(lists[0])
        }
    }

    fun getAllList(): List<CustomList>{
        val task = getAllListTask()
        task.execute()
        return task.get()
    }

    fun getGamesList(name: String): List<ArtistsAndGames> {
        val task = getGamesListTask()
        task.execute(name)
        return task.get()
    }

    fun insertList(nameList: String) = insertListTask().execute(nameList)
    fun insertGameinList(game: Game) = insertGameinListTask().execute(game)
    fun insertArtist(artistName: String, gameName: String) = insertArtistTask().execute(artistName, gameName)
    fun insertFavorite(nameFavList: String, publisher: String, designer: String) = insertFavoritesTask().execute(nameFavList,publisher,designer)
    fun insertGamesinFavorites(vararg games: Game) = insertGamesinFavoritesTask().execute(games)
    fun insertMechanics(vararg mechanics: Mechanics) = insertMechanicsinFavoritesTask().execute(mechanics)
    fun insertCategories(vararg categories: Categories) = insertCategoriesinFavoritesTask().execute(categories)

    fun deleteGamesinList(vararg games: Game) = deleteGamesinListTask().execute(*games)

    fun deleteList(list: CustomList) = deleteListTask().execute(list)

    fun search(
        name: String,
        limit: Int,
        skip: Int,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String
    ) {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.bgg.search(
            name,
            limit,
            skip,
            onSuccess,
            onError,
            url
        )
    }

    fun fromDto(dto: GameDto): ArtistsAndGames {
        val gameName = dto.name!!
        val game = Game(
            dto.id!!,
            "",
            "",
            gameName,
            dto.description!!,
            dto.avgUserRating,
            dto.yearPublished,
            dto.minPlayer,
            dto.maxPlayer,
            dto.minAge,
            dto.primaryPublisher,
            dto.rulesUrl,
            dto.url,
            dto.images?.small
        )
        val artists: ArrayList<Artist> = arrayListOf()
        dto.artists?.forEach {
            artists.add(Artist(gameName, it))
        }
        return ArtistsAndGames(game, artists)
    }

    fun getList(listName : String): CustomList{
        val task = getListTask()
        task.execute(listName)
        return task.get()
    }

}




