package pt.isel.pdm.li52d.g4.bgg

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.Categories
import pt.isel.pdm.li52d.g4.bgg.model.DesignersAndGames
import pt.isel.pdm.li52d.g4.bgg.model.Favorites
import pt.isel.pdm.li52d.g4.bgg.model.Mechanics

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

//
//    fun get(name: String){
//        this.name = "Fav $name"
//        favoritesLiveData.value = BggApp.CUSTOM_LIST_REPO.getGamesAndDesignersFavList(this.name).toTypedArray()
//    }
//


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
                    BggApp.CUSTOM_LIST_REPO.insertGamesinFavorites(game.game)
                    game.designerList.forEach{ designer ->
                        BggApp.CUSTOM_LIST_REPO.insertDesignerInFavorites(favListName, game.game.name, designer.designerName)
                    }
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

    fun observe(owner: LifecycleOwner, observer: (Array<Favorites>) -> Unit) {
        favoritesListsLiveData.observe(owner, Observer { observer(it) })
    }
}