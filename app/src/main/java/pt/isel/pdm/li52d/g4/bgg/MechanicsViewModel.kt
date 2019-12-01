package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.w3c.dom.NameList
import pt.isel.pdm.li52d.g4.bgg.model.Mechanics

class MechanicsViewModel: ViewModel() {
    private var mechanicsLiveData : MutableLiveData<Array<Mechanics>> = MutableLiveData(emptyArray())

    val mechanics : Array<Mechanics> get() = mechanicsLiveData.value!!
    var name = ""
    var url = ""

    fun getMechanics(nameList: String){
        mechanicsLiveData.value = BggApp.CUSTOM_LIST_REPO.getMechanics(nameList).toTypedArray()

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
            },
            url
        )
    }

    fun observe(owner: LifecycleOwner, observer: (Array<Mechanics>) -> Unit) {
        mechanicsLiveData.observe(owner, Observer { observer(it) })
    }


}