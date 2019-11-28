package pt.isel.pdm.li52d.g4.bgg.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*

class GameListActivity: AppCompatActivity() {

//   val bgg : BGGWebApi by lazy {
//       BGGWebApi(this)
//    }

    val model : GameViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[GameViewModel::class.java]
    }

    val adapter : GamesAdapter by lazy {
        GamesAdapter(model)
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
        if (PAGEACTIVITY == 1)
            buttonPrevious.visibility = Button.INVISIBLE
        else buttonPrevious.visibility = Button.VISIBLE
        //buttonPrevious.visibility = Button.INVISIBLE
        buttonPrevious.setOnClickListener {
            if(PAGEACTIVITY > 1 && PAGEACTIVITY == PAGEMODEL)
                --PAGEACTIVITY
            SKIP-=30
            finish()
            startActivity(intent)

        }


        val buttonNext = findViewById<Button>(R.id.button_next)
        buttonNext.text = "->"

        buttonNext.setOnClickListener {
            if(PAGEMODEL == PAGEACTIVITY ) {
                if (model.games.size < 30)
                    buttonNext.visibility = Button.INVISIBLE
                else ++PAGEACTIVITY

                SKIP += 30
                finish()
                startActivity(intent)
            }
        }
    }
}