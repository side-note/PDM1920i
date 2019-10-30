package g5.li22d.poo.isel.pt.bgg

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import g5.li22d.poo.isel.pt.bgg.dto.GameDto

class GameViewModel(private val bggWebApi: BGGWebApi) : ViewModel(){

    private var liveData : MutableLiveData<Array<GameDto>> = MutableLiveData(emptyArray())
    val games : Array<GameDto> get() = liveData.value!!
    var name = ""

    fun search(name: String, url: String) {
        if(this.name == name) return
        this.name = name

        Log.v(TAG, "**** FETCHING Games called by $name from BoardGameAtlas.com...")
        bggWebApi.search(
            name,
            {games ->
                Log.v(TAG, "**** FETCHING Games called by $name COMPLETED !!!!")
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