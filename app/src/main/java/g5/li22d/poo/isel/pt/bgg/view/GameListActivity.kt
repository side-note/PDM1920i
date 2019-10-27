package g5.li22d.poo.isel.pt.bgg.view

import android.content.Intent
import android.os.Bundle
import android.widget.TabHost
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
        ViewModelProviders.of(this)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)



        if(intent.getStringExtra(NAME)!= null) {
            bgg.searchGame(intent.getStringExtra(NAME), { games ->
                model.games = games.games
                adapter.notifyDataSetChanged()
            }, { err -> throw err })
        }

        if(intent.getStringExtra(MOST_POPULAR_GAMES)!= null) {
            bgg.mostPopularGame(intent.getStringExtra(MOST_POPULAR_GAMES), { games ->
                model.games = games.games
                adapter.notifyDataSetChanged()
            }, { err -> throw err })
        }

        if(intent.getStringExtra(PUBLISHER) != null){
            bgg.searchPublisher(intent.getStringExtra(PUBLISHER), { games ->
                model.games = games.games
                adapter.notifyDataSetChanged()
            }, {err -> throw err})
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}