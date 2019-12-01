package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.Categories

class CategoriesViewModel: ViewModel() {
    private var categoriesLiveData : MutableLiveData<Array<Categories>> = MutableLiveData(emptyArray())

    val categories : Array<Categories> get() = categoriesLiveData.value!!
    var name = ""
    var url = ""

    fun getCategories(nameList: String){
        categoriesLiveData.value = BggApp.CUSTOM_LIST_REPO.getCategories(nameList).toTypedArray()

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
            },
            url
        )
    }

    fun observe(owner: LifecycleOwner, observer: (Array<Categories>) -> Unit) {
        categoriesLiveData.observe(owner, Observer { observer(it) })
    }

}