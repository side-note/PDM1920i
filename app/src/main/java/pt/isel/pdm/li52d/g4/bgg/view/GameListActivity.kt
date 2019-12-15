package pt.isel.pdm.li52d.g4.bgg.view

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*
import android.view.WindowManager



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
        title.text = model.name.removePrefix("List ").removePrefix("Fav ")
        model.ctx = this
        model.observe(this){
            adapter.notifyDataSetChanged()
            buttonNext.visibility = Button.GONE
            buttonPrevious.visibility = Button.GONE

            if(!model.name.contains("List ") && !model.name.contains("Fav ")) {
                if (model.games.size < LIMIT)
                    buttonNext.visibility = Button.INVISIBLE
                else buttonNext.visibility = Button.VISIBLE
                if (PAGEACTIVITY == 1)
                    buttonPrevious.visibility = Button.INVISIBLE
                else buttonPrevious.visibility = Button.VISIBLE
            }
            else if(model.name.contains("List "))
                intent.putExtra("trashCan", false)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonPrevious.setOnClickListener {
            if(PAGEACTIVITY > 1 && PAGEACTIVITY == PAGEMODEL) {
                SKIP = (--PAGEACTIVITY-1) * 30
                model.gameSearch(model.name, model.url, LIMIT, SKIP)
                recyclerView.smoothScrollToPosition(0)
            }
        }

        buttonNext.setOnClickListener {
            if(model.games.size > 30 && PAGEMODEL == PAGEACTIVITY) {
                SKIP = (++PAGEACTIVITY-1) * 30
                model.gameSearch(model.name, model.url, LIMIT, SKIP)
                recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    fun doKeepDialog(dialog: Dialog) {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = lp
    }

}