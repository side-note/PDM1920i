package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BGGViewModelFactoryProvider(val intent: Intent) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> {
                val model = GameViewModel(  )
                val intentName: String? = intent.getStringExtra(NAME)
                val intentMPP: String? = intent.getStringExtra(MOST_POPULAR_GAMES)
                val intentPublisher: String? = intent.getStringExtra(PUBLISHER)
                val intentDesigner: String? = intent.getStringExtra(DESIGNER)
                val intentList: String? = intent.getStringExtra(LIST)
                val intentMechanics: String? = intent.getStringExtra(MECHANICS)
                val intentCategories: String? = intent.getStringExtra(CATEGORIES)
                val intentOptions: String? = intent.getStringExtra(OPTIONS)

                when {
                    intentName != null -> model.gameSearch(intentName, BGG_GET_GAMES, LIMIT, SKIP)
                    intentMPP != null -> model.gameSearch(intentMPP, BGG_MPP, LIMIT, SKIP)
                    intentPublisher != null -> model.gameSearch(intentPublisher, BGG_PUBLISHER, LIMIT, SKIP)
                    intentDesigner != null -> model.gameSearch(intentDesigner, BGG_DESIGNERS, LIMIT, SKIP)
                    intentList != null -> model.get(intentList)
                    intentMechanics != null -> model.mechanicsSearch(intentMechanics, BGG_MECHANICS)
                    intentCategories != null -> model.categoriesSearch(intentCategories, BGG_CATEGORIES)
                    intentOptions != null -> model.gameSearch(intentOptions, BGG_OPTIONS,0,0)
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