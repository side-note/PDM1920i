
package g5.li22d.poo.isel.pt.bgg


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TabHost
import androidx.lifecycle.ViewModelProviders
import g5.li22d.poo.isel.pt.bgg.view.GameListActivity


const val NAME : String = "Name"
const val MOST_POPULAR_GAMES : String = "Most Popular Games"

class MainActivity : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener {

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


    val bgg : BGGWebApi by lazy {
        BGGWebApi(this)
    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model)
    }

    val model : GameViewModel by lazy {
        ViewModelProviders.of(this)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerArtists)
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)

        //findViewById<SearchView>(R.id.search_bar).setOnClickListener(this)
        findViewById<SearchView>(R.id.search_bar).setOnQueryTextListener(this)
        findViewById<Button>(R.id.mpp).setOnClickListener(this)
    }
}
