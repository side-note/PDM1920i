package pt.isel.pdm.li52d.g4.bgg

import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto

class DetailedGameInfoViewModel(val dto : GameDto) : ViewModel() {
    val desc = dto.description
    val gameName = dto.name
    val rating = dto.avgUserRating
    val year = dto.yearPublished
    val minplayer = dto.minPlayer
    val maxplayer = dto.maxPlayer
    val minage = dto.minAge
    val publisher = dto.primaryPublisher
    val rulesurl = dto.rulesUrl
    val artists = dto.artists
    val url = dto.url
    val smallImage =dto.images!!.small
}