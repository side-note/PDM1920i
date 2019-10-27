package g5.li22d.poo.isel.pt.bgg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameActivity : AppCompatActivity() {


        val bgg: BGGWebApi by lazy {
            BGGWebApi(this)
        }
        val adapter : GamesAdapter by lazy {
            GamesAdapter(model)
        }
        val model : GameViewModel by lazy {
            ViewModelProviders.of(this)[GameViewModel::class.java]
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.games_layout)
            val mbid = intent.getStringExtra("GAME_MBID")

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerGames)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            /**
             * Setup recyclerArtists with ArtistsAdapter
             */
//            val mbid = intent.getStringExtra(ARTIST_MBID)
//            val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlbums)
//            recyclerView.adapter = adapter
//            recyclerView.layoutManager = LinearLayoutManager(this)
//            /**
//             * Setup artis name on top of the screen
//             */
//            val name= intent.getStringExtra(ARTIST_NAME)
//            findViewById<TextView>(R.id.txtAlbumsArtistName).text = name
            /**
             * Get albums from artist with mbid
//             */
//            Log.v(TAG, "**** FETCHING Albums $ from Last.fm...")
//            bgg.getGames(mbid, 1, {games ->
//                Log.v(TAG, "**** FETCHING Albums $ COMPLETED !!!!")
//                model.games = games.games.game
//                adapter.notifyDataSetChanged()
//            }, {err -> throw err})
        }
    }
