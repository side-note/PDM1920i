package pt.isel.pdm.li52d.g4.bgg.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.volley.VolleyError
import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.TAG
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto

class BGGRepository {
    fun getList(name : String) : List<ArtistsAndGames> {
        val res = BggApp.db.customListAndGamesDao().getGamesForCustomList(name)
        return res
    }
    fun insertList(
        nameList: String
    ) {
        BggApp.db.customListAndGamesDao().insertList(CustomList(nameList))
    }

    fun insertGame(
        game: Game
//        dto: GameDto,
//        listName: String
    ) {
//        val game = fromDto(listName, dto)
        BggApp.db.customListAndGamesDao().insertGame(game)
    }

    fun insertArtist(
        artistName: String,
        gameName: String
    ) {
        BggApp.db.customListAndGamesDao().insertArtist(Artist(gameName, artistName))
    }

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




