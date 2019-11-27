package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import pt.isel.pdm.li52d.g4.bgg.model.CustomListsAndGames

class GameViewModel() : ViewModel(){

    private var liveData : MutableLiveData<Array<ArtistsAndGames>> = MutableLiveData(emptyArray<ArtistsAndGames>())
    private var src: LiveData<List<CustomListsAndGames>>? = null
    val games : Array<ArtistsAndGames> get() = liveData.value!!
    var name = ""

    /*fun get(name: String){
        src = BggApp.CUSTOM_LIST_REPO.getList(name)
    }*/

    fun search(name: String, url: String) {

        if(this.name == name) return
        this.name = name

        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.search(
            name,
            {searchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val aux: ArrayList<ArtistsAndGames> = arrayListOf()
                searchDtoResult.games.forEach {
                    aux.add(BggApp.CUSTOM_LIST_REPO.fromDto(it))
                }
                this.liveData.value = aux.toTypedArray()
            },
            { this.name = ""; throw it },
            url
        )
    }

    fun observe(owner: LifecycleOwner, observer: (Array<ArtistsAndGames>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }

}