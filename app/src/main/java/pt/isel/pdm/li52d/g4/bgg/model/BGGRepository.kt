package pt.isel.pdm.li52d.g4.bgg.model

import android.util.Log
import pt.isel.pdm.li52d.g4.bgg.BggApp
import pt.isel.pdm.li52d.g4.bgg.TAG
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto

class BGGRepository {
    /*fun getList(name : String) : LiveData<List<CustomListsAndGames>>{
        val res = BggApp.db.customListAndGamesDao().getGamesForCustomList(name)
        return res
    }*/
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
        listName: String,
        name: String,
        url: String,
        addToDb: Boolean
    ): ArrayList<ArtistsAndGames> {
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        val ret = emptyList<ArtistsAndGames>() as ArrayList<ArtistsAndGames>
        BggApp.bgg.search(
            name,
            { games ->
                games.games.forEach { ret.add(fromDto(listName, it, addToDb)) }
            },
            { throw it },
            url
        )
        return ret
    }

    private fun fromDto(listName: String, dto: GameDto, addToDb: Boolean): ArtistsAndGames {
        val gameName = dto.name!!
        val game = Game(
            dto.id!!,
            listName,
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
        if(addToDb) insertGame(game)
        val artists: ArrayList<Artist> = emptyList<Artist>() as ArrayList<Artist>
        dto.artists?.forEach {
            artists.add(Artist(gameName, it))
            if(addToDb) insertArtist(gameName, it)
        }
        return ArtistsAndGames(game, artists)
    }

}




