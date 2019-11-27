package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.dto.SearchDto
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames

class ListsActivity : AppCompatActivity() {
    val model : DetailedGameInfoViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app,intent)
        ViewModelProviders.of(this, factory)[DetailedGameInfoViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_lists_layout)
        val button = findViewById<Button>(R.id.list)
        button.setText("List1")

        findViewById<Button>(R.id.list).setOnClickListener{
            if(intent.getStringExtra(LISTS) != null) {
                val intent = Intent(this, GameListActivity::class.java)
                intent.putExtra(LIST, button.text)
                startActivity(intent)
            } else{

//                class MyTask: AsyncTask<String, Int, ArtistsAndGames>() {
//
//                    override fun doInBackground(vararg text: String): ArtistsAndGames {
//                        val artistsAndGames = intent.getParcelableExtra<ArtistsAndGames>(GAME_NAME)
//                        artistsAndGames!!.game.nameList = text[0]
//                        return artistsAndGames
//                    }
//                    override fun onPostExecute(result: ArtistsAndGames) {
//                        BggApp.CUSTOM_LIST_REPO.insertGame(result.game)
//                        result.artistList.forEach {
//                            BggApp.CUSTOM_LIST_REPO.insertArtist(result.game.name, it.artistName)
//                        }
//                    }
//                }
//                val task =  MyTask()
//                task.execute(button.text.toString())
                val artistsAndGames = intent.getParcelableExtra<ArtistsAndGames>(GAME_NAME)
                artistsAndGames!!.game.nameList = button.text.toString()
                BggApp.CUSTOM_LIST_REPO.insertGame(artistsAndGames.game)
                artistsAndGames.artistList.forEach {
                    BggApp.CUSTOM_LIST_REPO.insertArtist(artistsAndGames.game.name, it.artistName)
                }
//                BggApp.CUSTOM_LIST_REPO.search(button.text.toString(), intent.getStringExtra(LISTS)!!, GAME_NAME, true)
                super.onBackPressed()
            }
        }
    }
}