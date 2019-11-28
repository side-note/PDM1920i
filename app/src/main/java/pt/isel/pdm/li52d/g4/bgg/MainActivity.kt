package pt.isel.pdm.li52d.g4.bgg


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SearchView
import pt.isel.pdm.li52d.g4.bgg.view.CreditsActivity
import pt.isel.pdm.li52d.g4.bgg.view.GameListActivity
import pt.isel.pdm.li52d.g4.bgg.view.ListActivity
import pt.isel.pdm.li52d.g4.bgg.view.ListsActivity

const val NAME : String = "Name"
const val MOST_POPULAR_GAMES : String = "Most Popular Game"
const val PUBLISHER : String = "Publisher"
const val ARTIST :String = "Artist"
const val TAG : String = "BGG"
const val CREDITS : String = "Credits"
const val LISTS : String = "Lists"
const val LIST : String = "List"
const val ILIST : String = "IListSelect"
const val LIMIT : Int = 31
var SKIP: Int = 0


class MainActivity() : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener, IListSelect {

    override fun selectList(b: Button) {
        val intent = Intent(this, GameListActivity::class.java)
        intent.putExtra(LIST, b.text)
        startActivity(intent)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int){
//        dest?.writeValue(ctx)
    }

    override fun describeContents(): Int = 0

    override fun onClick(v: View?) {
        val myIntent = Intent(this, GameListActivity::class.java)
        myIntent.putExtra(MOST_POPULAR_GAMES,"popularity")
        startActivity(myIntent)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.e("onQueryTextChange", newText)
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        val myIntent = Intent(this, GameListActivity::class.java)
        myIntent.putExtra(NAME,query)
        startActivity(myIntent)
        return true
    }

//    val bgg : BGGWebApi by lazy {
//        BGGWebApi(this)
//    }

    constructor(parcel: Parcel) : this()//(parcel.readValue(ClassLoader.getSystemClassLoader()) as Context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findViewById<SearchView>(R.id.search_bar).setOnQueryTextListener(this)

        findViewById<Button>(R.id.mpp).setOnClickListener(this)

        findViewById<Button>(R.id.credits).setOnClickListener{
            val myIntent = Intent(this, CreditsActivity::class.java)
            myIntent.putExtra(CREDITS,"Credits")
            startActivity(myIntent)
        }

        findViewById<Button>(R.id.lists).setOnClickListener{
            val intent = Intent(this, ListsActivity::class.java)
//            intent.putExtra(LISTS, "Lists")
            intent.putExtra(ILIST, this)
            startActivity(intent)
        }
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
