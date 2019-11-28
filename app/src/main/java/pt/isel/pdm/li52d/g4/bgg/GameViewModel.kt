package pt.isel.pdm.li52d.g4.bgg

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

class GameViewModel : ViewModel(){

    private var liveData : MutableLiveData<Array<ArtistsAndGames>> = MutableLiveData(emptyArray())
    val games : Array<ArtistsAndGames> get() = liveData.value!!
    var name = ""
    var url = ""

    fun get(name: String){
        liveData.value = BggApp.CUSTOM_LIST_REPO.getGamesList(name).toTypedArray()
//        class MyTask: AsyncTask<String, Unit, Array<ArtistsAndGames>>() {
//
//            override fun doInBackground(vararg str: String) : Array<ArtistsAndGames> {
//                return
//            }
//
//            override fun onPostExecute(result: Array<ArtistsAndGames>?) {
//
//            }
//
//        }
//        val task = MyTask()
//        task.execute(name)
    }

    fun search(name: String, url: String, limit: Int, skip: Int) {
        val pageModel = (skip/30) + 1
        if(PAGEMODEL == pageModel) return
        this.name = name
        this.url = url
        Log.v(TAG, "**** FETCHING Game called by $name from BoardGameAtlas.com...")
        BggApp.CUSTOM_LIST_REPO.search(
            name,
            limit,
            skip,
            {searchDtoResult ->
                Log.v(TAG, "**** FETCHING Game called by $name COMPLETED !!!!")
                val aux: ArrayList<ArtistsAndGames> = arrayListOf()
                searchDtoResult.games.forEach {
                    aux.add(BggApp.CUSTOM_LIST_REPO.fromDto(it))
                }
                this.liveData.value = aux.toTypedArray()
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

    fun observe(owner: LifecycleOwner, observer: (Array<ArtistsAndGames>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }

}