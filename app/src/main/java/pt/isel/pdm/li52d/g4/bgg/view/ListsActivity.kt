package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

class ListsActivity : AppCompatActivity() {
    val model : DetailedGameInfoViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app,intent)
        ViewModelProviders.of(this, factory)[DetailedGameInfoViewModel::class.java]
    }

    val adapter : ListsAdapter by lazy {
        ListsAdapter(model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_lists_layout)
        val button = findViewById<Button>(R.id.list)
        val listName = "List1"

        model.observe(this){adapter.notifyDataSetChanged()}
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*class MyTask: AsyncTask<String, Unit, Unit>() {
            override fun doInBackground(vararg nameList: String) {
                BggApp.CUSTOM_LIST_REPO.insertList(nameList[0])
            }
        }
        MyTask().execute(listName)
        button.setText(listName)

        findViewById<Button>(R.id.list).setOnClickListener{
            if(intent.getStringExtra(LISTS) != null) {
                val intent = Intent(this, GameListActivity::class.java)
                intent.putExtra(LIST, button.text)
                startActivity(intent)
            } else{

                class MyTask: AsyncTask<Intent, Unit, Unit>() {

                    override fun doInBackground(vararg intent: Intent) {
                        val artistsAndGames = intent[0].getParcelableExtra<ArtistsAndGames>(GAME_NAME)
                        artistsAndGames!!.game.nameList = intent[0].getStringExtra(LIST)!!
                        BggApp.CUSTOM_LIST_REPO.insertGame(artistsAndGames.game)
                        artistsAndGames.artistList.forEach {
                            BggApp.CUSTOM_LIST_REPO.insertArtist(artistsAndGames.game.name, it.artistName)
                        }
                    }

                }
                val task =  MyTask()
                intent.putExtra(LIST, button.text.toString())
                task.execute(intent)
//                val artistsAndGames = intent.getParcelableExtra<ArtistsAndGames>(GAME_NAME)
//                artistsAndGames!!.game.nameList = button.text.toString()
//                BggApp.CUSTOM_LIST_REPO.insertGame(artistsAndGames.game)
//                artistsAndGames.artistList.forEach {
//                    BggApp.CUSTOM_LIST_REPO.insertArtist(artistsAndGames.game.name, it.artistName)
//                }
//                BggApp.CUSTOM_LIST_REPO.search(button.text.toString(), intent.getStringExtra(LISTS)!!, GAME_NAME, true)
                super.onBackPressed()
            }
        }*/
    }
}