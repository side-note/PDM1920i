package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BGGViewModelFactoryProvider(val intent: Intent) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> {
                val model = GameViewModel()
                val intentName: String? = intent.getStringExtra(NAME)
                val intentMPP: String? = intent.getStringExtra(MOST_POPULAR_GAMES)
                val intentPublisher: String? = intent.getStringExtra(PUBLISHER)
                val intentDesigner: String? = intent.getStringExtra(DESIGNER)
                val intentList: String? = intent.getStringExtra(LIST)
                val intentFavorite: String? = intent.getStringExtra(FAVORITE)

                when {
                    intentName != null -> model.gameSearch(intentName, BGG_GET_GAMES, LIMIT, SKIP)
                    intentMPP != null -> model.gameSearch(intentMPP, BGG_MPP, LIMIT, SKIP)
                    intentPublisher != null -> model.gameSearch(intentPublisher, BGG_PUBLISHER, LIMIT, SKIP)
                    intentDesigner != null -> model.gameSearch(intentDesigner, BGG_DESIGNERS, LIMIT, SKIP)
                    intentList != null -> model.getList(intentList)
                    intentFavorite != null -> model.getFav(intentFavorite)
                }

                model as T
            }
            MechanicsViewModel::class.java -> {
                val model = MechanicsViewModel()
                val intentMechanics: String? = intent.getStringExtra(MECHANICS)
                if (intentMechanics != null){ model.mechanicsSearch(intentMechanics, BGG_MECHANICS)}
                model as T
            }
            CategoriesViewModel::class.java -> {
                val model = CategoriesViewModel()
                val intentCategories: String? = intent.getStringExtra(CATEGORIES)
                if (intentCategories != null) model.categoriesSearch(intentCategories, BGG_CATEGORIES)
                model as T
            }
            FavoritesViewModel::class.java -> {
                val model = FavoritesViewModel()
                model.getAllFavoritesList()
                model as T
            }
            DetailedGameInfoViewModel::class.java ->
                DetailedGameInfoViewModel(intent.getParcelableExtra(GAME_NAME)!!) as T
            ListViewModel::class.java -> {
                val model = ListViewModel()
                model.getAllLists()
                model as T
            }
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}