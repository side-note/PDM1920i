package g5.li22d.poo.isel.pt.bgg.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import g5.li22d.poo.isel.pt.bgg.*

class GameListActivity: AppCompatActivity() {

    val bgg : BGGWebApi by lazy {
        BGGWebApi(this)
    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model)
    }
    val model : GameViewModel by lazy {
        val app = application as GeniuzApp
        val factory = BGGViewModelFactoryProvider(app)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)



        if(intent.getStringExtra(NAME)!= null) {
            model.observe(this){adapter.notifyDataSetChanged()}
            model.search( intent.getStringExtra(NAME)!!, BGG_GET_GAMES)
        }
        else if(intent.getStringExtra(MOST_POPULAR_GAMES)!= null) {
            model.observe(this){adapter.notifyDataSetChanged()}
            model.search( intent.getStringExtra(MOST_POPULAR_GAMES)!!, BGG_MPP)
        }

        else if(intent.getStringExtra(PUBLISHER) != null){
            model.observe(this){adapter.notifyDataSetChanged()}
            model.search( intent.getStringExtra(PUBLISHER)!!, BGG_PUBLISHER)
        }
        else if(intent.getStringExtra(ARTIST) != null){
            model.observe(this){adapter.notifyDataSetChanged()}
            model.search( intent.getStringExtra(ARTIST)!!, BGG_ARTIST)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}