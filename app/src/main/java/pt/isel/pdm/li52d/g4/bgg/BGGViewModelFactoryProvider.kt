package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto


class BGGViewModelFactoryProvider(val intent: Intent) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> {
                val model = GameViewModel(  )
                val intentName: String? = intent.getStringExtra(NAME)
                val intentMPP: String? = intent.getStringExtra(MOST_POPULAR_GAMES)
                val intentPublisher: String? = intent.getStringExtra(PUBLISHER)
                val intentArtist: String? = intent.getStringExtra(ARTIST)
                val intentList: String? = intent.getStringExtra(LIST)

                when {
                    intentName != null -> model.search(intentName, BGG_GET_GAMES, LIMIT, SKIP)
                    intentMPP != null -> model.search(intentMPP, BGG_MPP, LIMIT, SKIP)
                    intentPublisher != null -> model.search(intentPublisher, BGG_PUBLISHER, LIMIT, SKIP)
                    intentArtist != null -> model.search(intentArtist, BGG_ARTIST, LIMIT, SKIP)
                    intentList != null -> model.get(intentList)
                }

                model as T
            }
            DetailedGameInfoViewModel::class.java -> DetailedGameInfoViewModel(intent.getParcelableExtra(GAME_NAME)!!) as T
            ListViewModel::class.java -> {
                val model = ListViewModel()
                model.getAllLists()
                model as T
            }
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}