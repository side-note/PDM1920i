package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.model.Categories
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames
import pt.isel.pdm.li52d.g4.bgg.model.Mechanics

class GameViewModel : ViewModel(){

    private var gameLiveData : MutableLiveData<Array<DesignersAndGames>> = MutableLiveData(emptyArray())

    val games : Array<DesignersAndGames> get() = gameLiveData.value!!
    var name = ""
    var url = ""
    var ctx: Context? = null



    fun getList(name: String){
//        if(this.name == "List $name") return
        this.name = "List $name"
        gameLiveData.value = BggApp.CUSTOM_LIST_REPO.getGamesAndDesignersList(name).toTypedArray()
    }

    fun getFav(name: String){
        this.name = "Fav $name"
       gameLiveData.value = BggApp.CUSTOM_LIST_REPO.getGamesAndDesignersFavList(this.name).toTypedArray()
    }

    fun gameSearch(name: String, url: String, limit: Int, skip: Int) {
        val pageModel = (skip/30) + 1
        if(PAGEMODEL == pageModel && this.name == name) return
        this.name = name
        this.url = url
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.gameSearch(
            name,
            limit,
            skip,
            {searchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val auxes: ArrayList<DesignersAndGames> = arrayListOf()
                searchDtoResult.games.forEach {
                    auxes.add(BggApp.CUSTOM_LIST_REPO.fromGameDto(it))
                }

                this.gameLiveData.value = auxes.toTypedArray()
                PAGEMODEL = pageModel
            },
            {
                this.name = ""
                Toast.makeText(ctx, it.message, Toast.LENGTH_LONG).show()
            },
            url
        )

    }

    fun observe(owner: LifecycleOwner, observer: (Array<DesignersAndGames>) -> Unit) {
        gameLiveData.observe(owner, Observer { observer(it) })
    }

}