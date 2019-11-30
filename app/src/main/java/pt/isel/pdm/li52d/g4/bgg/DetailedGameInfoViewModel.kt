package pt.isel.pdm.li52d.g4.bgg

import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames

class DetailedGameInfoViewModel(val gameAndDesigners: DesignersAndGames) : ViewModel() {
    val desc = gameAndDesigners.game.desc
    val gameName = gameAndDesigners.game.name
    val rating = gameAndDesigners.game.rating
    val year = gameAndDesigners.game.year
    val minplayer = gameAndDesigners.game.minplayer
    val maxplayer = gameAndDesigners.game.maxplayer
    val minage = gameAndDesigners.game.minage
    val publisher = gameAndDesigners.game.publisher
    val rulesurl = gameAndDesigners.game.rulesurl
    val designers = gameAndDesigners.designerList
    val url = gameAndDesigners.game.url
    val smallImage =gameAndDesigners.game.smallImage
}