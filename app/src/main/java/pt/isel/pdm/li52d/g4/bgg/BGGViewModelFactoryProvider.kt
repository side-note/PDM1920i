package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto


class BGGViewModelFactoryProvider (val app : BggApp, val intent: Intent) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> {
                val model = GameViewModel(app.bgg)

                val intentName : String? = intent.getStringExtra(NAME)
                val intentMPP : String? = intent.getStringExtra(MOST_POPULAR_GAMES)
                val intentPublisher : String? = intent.getStringExtra(PUBLISHER)
                val intentArtist : String? = intent.getStringExtra(ARTIST)

                when {
                    intentName != null -> model.search( intentName, BGG_GET_GAMES)
                    intentMPP != null -> model.search( intentMPP, BGG_MPP)
                    intentPublisher != null -> model.search( intentPublisher, BGG_PUBLISHER)
                    intentArtist != null -> model.search( intentArtist, BGG_ARTIST)
                }

                model as T
            }
            DetailedGameInfoViewModel::class.java -> DetailedGameInfoViewModel(intent.getParcelableExtra(GAME_NAME)!!) as T
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}