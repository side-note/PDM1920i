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

//    val bgg : BGGWebApi by lazy {
//        BGGWebApi(this)
//    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model)
    }
    val model : GameViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app, intent)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)
       // val title : TextView = findViewById(R.id.title_view)


        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}