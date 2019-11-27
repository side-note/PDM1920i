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

    private class insertGameTask : AsyncTask<Game, Unit, Unit>() {
        override fun doInBackground(vararg game: Game) {
            BggApp.db.customListAndGamesDao().insertGame(game[0])
        }
    }

    private class insertArtistTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg params: String) {
            BggApp.db.customListAndGamesDao().insertArtist(Artist(params[0], params[1]))
        }
    }
    private class getGamesListTask : AsyncTask<String, Unit, List<ArtistsAndGames>>() {
        override fun doInBackground(vararg name: String): List<ArtistsAndGames> {
            return BggApp.db.customListAndGamesDao().getGamesForCustomList(name[0])
        }
    }
    private class getAllListTask : AsyncTask<Unit, Unit, List<CustomList>>() {
        override fun doInBackground(vararg dummy : Unit): List<CustomList> {
            return BggApp.db.customListAndGamesDao().getCustomList()
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
    fun insertGame(game: Game) = insertGameTask().execute(game)
    fun insertArtist(artistName: String, gameName: String) = insertArtistTask().execute(artistName, gameName)

    fun search(
        name: String,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit,
        url: String
    ) {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.bgg.search(
            name,
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

}




