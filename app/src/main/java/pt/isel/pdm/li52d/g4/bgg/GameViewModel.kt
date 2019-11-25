package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import pt.isel.pdm.li52d.g4.bgg.model.CustomListGameJoinRepository
import pt.isel.pdm.li52d.g4.bgg.model.CustomListsGameJoin

class GameViewModel(private val bggWebApi: BGGWebApi) : ViewModel(){

    private var liveData : MutableLiveData<Array<GameDto>> = MutableLiveData(emptyArray())
    private var src: LiveData<List<CustomListsGameJoin>>? = null
    val games : Array<GameDto> get() = liveData.value!!
    var name = ""

    fun get(name: String){
        src = BggApp.customListRepo.getList(name)
    }

    fun search(name: String, url: String) {

        if(this.name == name) return
        this.name = name

        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        bggWebApi.search(
            name,
            {games ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                this.liveData.value = games.games
            },
            { this.name = ""; throw it },
            url
        )
    }

    fun observe(owner: LifecycleOwner, observer: (Array<GameDto>) -> Unit) {
        liveData.observe(owner, Observer { observer(it!!) })
    }

}