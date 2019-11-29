package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*

class GameListActivity: AppCompatActivity() {

    val model : GameViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model, intent)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games_layout)
        val title : TextView = findViewById(R.id.title_view)
        val buttonNext = findViewById<Button>(R.id.button_next)
        val buttonPrevious = findViewById<Button>(R.id.button_previous)
//        val deleteGame = findViewById<ImageView>(R.id.delete_game)
//        this code is a fail when trying to capitalize the first letter in every word of the name
//        val words = model.name.split(" ")
//        for (str in words){
//        str. = str[0].toUpperCase()
//        }
        title.text = model.name.removePrefix("List ")
        model.ctx = this
        model.observe(this){
            adapter.notifyDataSetChanged()
            if(!model.name.contains("List ")) {
                if (model.games.size < LIMIT)
                    buttonNext.visibility = Button.INVISIBLE
                else buttonNext.visibility = Button.VISIBLE
                if (PAGEACTIVITY == 1)
                    buttonPrevious.visibility = Button.INVISIBLE
                else buttonPrevious.visibility = Button.VISIBLE
//                deleteGame.visibility = ImageView.INVISIBLE
            }
            else{
                intent.putExtra("trashCan", false)
                buttonNext.visibility = Button.GONE
                buttonPrevious.visibility = Button.GONE
            }
        }
//        intent.putExtra("ctx", )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonPrevious.setOnClickListener {
            if(PAGEACTIVITY > 1 && PAGEACTIVITY == PAGEMODEL) {
                SKIP = (--PAGEACTIVITY-1) * 30
                model.search(model.name, model.url, LIMIT, SKIP)
                recyclerView.smoothScrollToPosition(0)
            }
        }

        buttonNext.setOnClickListener {
            if(model.games.size > 30 && PAGEMODEL == PAGEACTIVITY) {
                SKIP = (++PAGEACTIVITY-1) * 30
                model.search(model.name, model.url, LIMIT, SKIP)
                recyclerView.smoothScrollToPosition(0)
            }
        }

//        deleteGame.setOnClickListener {
//
//            BggApp.CUSTOM_LIST_REPO.deleteGamesinList()
//
//        }
    }

}