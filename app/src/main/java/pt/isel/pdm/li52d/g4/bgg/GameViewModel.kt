package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.model.Categories
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames
import pt.isel.pdm.li52d.g4.bgg.model.Mechanics

class GameViewModel : ViewModel(){

    private var gameLiveData : MutableLiveData<Array<DesignersAndGames>> = MutableLiveData(emptyArray())

    private var mechanicsLiveData : MutableLiveData<Array<Mechanics>> = MutableLiveData(emptyArray())

    private var categoriesLiveData : MutableLiveData<Array<Categories>> = MutableLiveData(emptyArray())
    val games : Array<DesignersAndGames> get() = gameLiveData.value!!
    var name = ""
    var url = ""
    var ctx: Context? = null

    fun get(name: String){
//        if(this.name == "List $name") return
        this.name = "List $name"
        gameLiveData.value = BggApp.CUSTOM_LIST_REPO.getGamesAndDesignersList(name).toTypedArray()
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
                throw it
                //Toast.makeText(, it.message, Toast.LENGTH_LONG).show()
            },
            url
        )

    }

    fun mechanicsSearch(name: String, url: String) {
        if(this.name == name) return
        this.name = name
        this.url = url
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.mechanicsSearch(
            name,
            {mechanicsSearchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val auxes: ArrayList<Mechanics> = arrayListOf()
                mechanicsSearchDtoResult.mechanics.forEach {
                    auxes.add(BggApp.CUSTOM_LIST_REPO.fromMechanicsDto(it))
                }

                this.mechanicsLiveData.value = auxes.toTypedArray()
            },
            {
                this.name = ""
                throw it
                //Toast.makeText(, it.message, Toast.LENGTH_LONG).show()
            },
            url
        )
    }

    fun categoriesSearch(name: String, url: String) {
        if(this.name == name) return
        this.name = name
        this.url = url
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.categoriesSearch(
            name,
            {categoriesSearchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val auxes: ArrayList<Categories> = arrayListOf()
                categoriesSearchDtoResult.categories.forEach {
                    auxes.add(BggApp.CUSTOM_LIST_REPO.fromCategoriesDto(it))
                }

                this.categoriesLiveData.value = auxes.toTypedArray()
            },
            {
                this.name = ""
                throw it
                //Toast.makeText(, it.message, Toast.LENGTH_LONG).show()
            },
            url
        )
    }

    fun observe(owner: LifecycleOwner, observer: (Array<DesignersAndGames>) -> Unit) {
        gameLiveData.observe(owner, Observer { observer(it) })
    }

}