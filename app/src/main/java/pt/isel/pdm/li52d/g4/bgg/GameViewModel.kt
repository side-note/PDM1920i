package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import pt.isel.pdm.li52d.g4.bgg.model.CustomListsAndGames

class GameViewModel() : ViewModel(){

    private var liveData : MutableLiveData<ArrayList<ArtistsAndGames>> = MutableLiveData(emptyList<ArtistsAndGames>() as ArrayList<ArtistsAndGames>)
    private var src: LiveData<List<CustomListsAndGames>>? = null
    val games : ArrayList<ArtistsAndGames> get() = liveData.value!!
    var name = ""

    /*fun get(name: String){
        src = BggApp.CUSTOM_LIST_REPO.getList(name)
    }*/

    fun search(listName: String, name: String, url: String, addToDb: Boolean) {

        if(this.name == name) return
        this.name = name

        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        liveData.value = BggApp.CUSTOM_LIST_REPO.search(
            listName,
            name,
//            {games ->
//                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
//                this.liveData.value = games.games
//            },
//            { this.name = ""; throw it },
            url,
            addToDb
        )
    }

    fun observe(owner: LifecycleOwner, observer: (ArrayList<ArtistsAndGames>) -> Unit) {
        liveData.observe(owner, Observer { observer(it!!) })
    }

}