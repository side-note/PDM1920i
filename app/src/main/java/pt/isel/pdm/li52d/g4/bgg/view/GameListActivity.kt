package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.BGGWebApi
import pt.isel.pdm.li52d.g4.bgg.*

class GameListActivity: AppCompatActivity() {

    val bgg : BGGWebApi by lazy {
        BGGWebApi(this)
    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model)
    }
    val model : GameViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)
        val title : TextView = findViewById(R.id.title_view)
        val intentName : String? = intent.getStringExtra(NAME)
        val intentMPP : String? = intent.getStringExtra(MOST_POPULAR_GAMES)
        val intentPublisher : String? = intent.getStringExtra(PUBLISHER)
        val intentArtist : String? = intent.getStringExtra(ARTIST)

        if(intentName != null){
                title.text = intentName
                model.observe(this){adapter.notifyDataSetChanged()}
                model.search( intentName, BGG_GET_GAMES)
        }
        else if(intentMPP != null ){
                title.text = resources.getString(R.string.most_popular_games)
                model.observe(this){adapter.notifyDataSetChanged()}
                model.search( intentMPP, BGG_MPP)
        }
        else if(intentPublisher != null) {
                title.text = intentPublisher
                model.observe(this){adapter.notifyDataSetChanged()}
                model.search( intentPublisher, BGG_PUBLISHER)
        }
        else if(intentArtist != null) {
                title.text = intentArtist
                model.observe(this){adapter.notifyDataSetChanged()}
                model.search( intentArtist, BGG_ARTIST)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}