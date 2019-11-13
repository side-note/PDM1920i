package pt.isel.pdm.li52d.g4.bgg

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BGGViewModelFactoryProvider (val app : BggApp, val intent: Intent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            GameViewModel::class.java -> GameViewModel(app.bgg) as T
            else -> throw IllegalArgumentException("There is no ViewModel for class $modelClass")
        }
    }
}