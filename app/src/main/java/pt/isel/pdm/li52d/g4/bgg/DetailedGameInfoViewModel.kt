package pt.isel.pdm.li52d.g4.bgg

import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

class DetailedGameInfoViewModel(val gameAndArtist: ArtistsAndGames) : ViewModel() {
    val desc = gameAndArtist.game.desc
    val gameName = gameAndArtist.game.name
    val rating = gameAndArtist.game.rating
    val year = gameAndArtist.game.year
    val minplayer = gameAndArtist.game.minplayer
    val maxplayer = gameAndArtist.game.maxplayer
    val minage = gameAndArtist.game.minage
    val publisher = gameAndArtist.game.publisher
    val rulesurl = gameAndArtist.game.rulesurl
    val artists = gameAndArtist.artistList
    val url = gameAndArtist.game.url
    val smallImage =gameAndArtist.game.smallImage
}