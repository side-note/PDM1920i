package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.*

class FavoritesViewModel: ViewModel() {
    var favoritesLiveData : MutableLiveData<Array<DesignersAndGames>> = MutableLiveData(emptyArray())
    val favoritesListsLiveData : MutableLiveData<Array<Favorites>> = MutableLiveData(emptyArray())
    val favorites : Array<Favorites> get() = favoritesListsLiveData.value!!
    var ctx : Context? = null
    var name = ""
    var url = ""
    var publisher = ""
    var designer = ""
    var mechanics = ""
    var categories = ""
    var mechanicsNames = ""
    var categoriesNames =""

    fun favoriteSearch(
        favListName: String,
        publisher: String,
        designer: String,
        mechanics: String,
        categories: String,
        mechanicsNames: String,
        categoriesNames : String
    ) {
        this.name = favListName
        this.publisher = publisher
        this.designer = designer
        this.mechanics = mechanics
        this.categories = categories
        this.mechanicsNames = mechanicsNames
        this.categoriesNames = categoriesNames
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.favoriteSearch(
            publisher,
            designer,
            mechanics,
            categories,
            {searchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val auxes: ArrayList<DesignersAndGames> = arrayListOf()
                searchDtoResult.games.forEach {
                    val game = BggApp.CUSTOM_LIST_REPO.fromGameDto(it)
                    game.game.nameFavListGame = favListName
                    auxes.add(game)
                    val mechName = mechanicsNames.split(",")
                    val mech = mechanics.split(",")
                    for(i in mech.indices)
                        BggApp.CUSTOM_LIST_REPO.insertMechanics(Mechanics(favListName ,mech[i],mechName[i],"true"))
                    val catName = categoriesNames.split(",")
                    val cat = categories.split(",")
                    for(i in cat.indices)
                        BggApp.CUSTOM_LIST_REPO.insertCategories(Categories(favListName ,cat[i],catName[i],"true"))

                }
                this.favoritesLiveData.value = auxes.toTypedArray()
                var count = 0
                auxes.forEach{
                    if (count < 5){
                        BggApp.CUSTOM_LIST_REPO.insertGamesinFavorites(it.game)
                        it.designerList.forEach{ designer ->
                            BggApp.CUSTOM_LIST_REPO.insertDesignerInFavorites(favListName, it.game.name, designer.designerName)
                        }
                    }
                    count++
                }
            },
            {
                this.name = ""
                throw it
            }
        )

    }

    fun getAllFavoritesList(){
        favoritesListsLiveData.value = BggApp.CUSTOM_LIST_REPO.getAllFavs().toTypedArray()
    }

    fun observeGames(owner: LifecycleOwner, observer: (Array<DesignersAndGames>) -> Unit) {
        favoritesLiveData.observe(owner, Observer { observer(it) })
    }

    fun observe(owner: LifecycleOwner, observer: (Array<Favorites>) -> Unit) {
        favoritesListsLiveData.observe(owner, Observer { observer(it) })
    }
}