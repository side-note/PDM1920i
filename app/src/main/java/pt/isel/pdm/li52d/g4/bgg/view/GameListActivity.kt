package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
        val factory = BGGViewModelFactoryProvider(app, intent)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)
        val title : TextView = findViewById(R.id.title_view)
        title.text = model.name


        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val buttonPrevious = findViewById<Button>(R.id.button_previous)
        buttonPrevious.text = "<-"
            buttonPrevious.isVisible =false
        buttonPrevious.setOnClickListener {
            SKIP-=30
        }

         val buttonNext = findViewById<Button>(R.id.button_next)
        buttonNext.text = "->"
        if(model.games.size < LIMIT)
            buttonNext.isVisible = false
        buttonNext.setOnClickListener {
            buttonPrevious.isVisible=true
            SKIP+=30

        }
    }
}