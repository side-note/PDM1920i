package pt.isel.pdm.li52d.g4.bgg

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import pt.isel.pdm.li52d.g4.bgg.model.CustomList
import pt.isel.pdm.li52d.g4.bgg.model.CustomListsAndGames

class ListViewModel {
    private var liveData : MutableLiveData<Array<CustomList>> = MutableLiveData(emptyArray())
    fun observe(owner: LifecycleOwner, observer: (Array<CustomList>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }


}
