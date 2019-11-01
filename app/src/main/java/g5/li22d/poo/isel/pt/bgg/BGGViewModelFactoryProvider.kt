package g5.li22d.poo.isel.pt.bgg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BGGViewModelFactoryProvider (val app : BggApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> GameViewModel(app.bgg) as T
//            ArtistsViewModel::class.java -> ArtistsViewModel(app.lastfm) as T
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}