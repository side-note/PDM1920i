package pt.isel.pdm.li52d.g4.bgg


import android.app.Activity
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
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
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
const val FROM_MAIN_ACTIVITY : String = "this intent is from MainActivity"
const val FROM_DETAILED_ACTIVITY : String = "this intent is from DetailedGameInfoActivity"
const val LIMIT : Int = 31
var PAGEMODEL: Int = 0
var SKIP: Int = 0
var PAGEACTIVITY: Int = 1


class MainActivity() : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener {

    override fun onClick(v: View?) {
        PAGEACTIVITY = 1
        PAGEMODEL = 0
        SKIP = 0
        val myIntent = Intent(this, GameListActivity::class.java)
        myIntent.putExtra(MOST_POPULAR_GAMES,"popularity")
        startActivity(myIntent)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.e("onQueryTextChange", newText)
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        PAGEACTIVITY = 1
        PAGEMODEL = 0
        SKIP = 0
        val myIntent = Intent(this, GameListActivity::class.java)
        myIntent.putExtra(NAME,query)
        startActivity(myIntent)
        return true
    }

    override fun onBackPressed() {
        intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findViewById<SearchView>(R.id.search_bar).setOnQueryTextListener(this)

        findViewById<Button>(R.id.mpp).setOnClickListener(this)

        findViewById<Button>(R.id.credits).setOnClickListener {
            val myIntent = Intent(this, CreditsActivity::class.java)
            myIntent.putExtra(CREDITS, "Credits")
            startActivity(myIntent)
        }

        findViewById<Button>(R.id.lists).setOnClickListener {
            val intent = Intent(this, ListsActivity::class.java)
            intent.putExtra(ILIST, IntentFromMain())
            startActivity(intent)
        }
    }
}

class IntentFromMain() : IListSelect{

    override var ctx: Context? = null
    override var act: Activity? = null
    override var artistsAndGames: ArtistsAndGames? = null

    override fun selectList(listName: String) {
        val intent = Intent(ctx!!, GameListActivity::class.java)
        intent.putExtra(LIST, listName)
        ctx!!.startActivity(intent)
    }

    constructor(parcel: Parcel) : this()
    override fun writeToParcel(dest: Parcel, flags: Int){ dest.writeValue(ctx) }
    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<IntentFromMain> {
        override fun createFromParcel(parcel: Parcel): IntentFromMain = IntentFromMain(parcel)
        override fun newArray(size: Int): Array<IntentFromMain?> = arrayOfNulls(size)
    }

}
