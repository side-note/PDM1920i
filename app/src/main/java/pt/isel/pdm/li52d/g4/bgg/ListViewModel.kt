package pt.isel.pdm.li52d.g4.bgg

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import pt.isel.pdm.li52d.g4.bgg.model.CustomList

class ListViewModel() : ViewModel( ){
    private var liveData : MutableLiveData<Array<CustomList>> = MutableLiveData(emptyArray())
    val lists : Array<CustomList> get() = liveData.value!!

    fun getAllLists() {
        liveData.value = BggApp.CUSTOM_LIST_REPO.getAllList().toTypedArray()
    }

    fun observe(owner: LifecycleOwner, observer: (Array<CustomList>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }
}
