package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detailed_info.*
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.model.Artist
import java.math.BigDecimal
import java.math.RoundingMode


class DetailedGameInfoActivity : AppCompatActivity(){

    val model : DetailedGameInfoViewModel by lazy {
        val app = application as BggApp
        val factory = BGGViewModelFactoryProvider(app,intent)
        ViewModelProviders.of(this, factory)[DetailedGameInfoViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_info)
        findViewById<Button>(R.id.add).setOnClickListener{
            val intent = Intent(this, ListsActivity::class.java)
            intent.putExtra(GAME_NAME, model.gameAndArtist)
            startActivity(intent)
        }


        val name: TextView =  findViewById(R.id.name_id)
        val description: TextView = findViewById(R.id.game_description)
        val rating: TextView = findViewById(R.id.detailed_rating)
        val img : ImageView = findViewById(R.id.game_image)
        /*
        * GameListActivity:
        *   Passar os intents e observer para a factory, para o caso de o ViewModel ja estar criado nao o recriar
        *
        * DetailedGameInfoActivity:
        *   Criar um ViewModel e fazer o intent para o caso de o ViewModel ja estar criado nao o refazer (similar à nota acima)
        * */

        Glide.with(this)
            .load(model.smallImage)
            .into(game_image)
        description.text = model.desc

        description.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        name.text = model.gameName
        rating.text =  " " + BigDecimal(model.rating.toString()).setScale(1, RoundingMode.HALF_UP).toDouble().toString() + "/5"
        year_published.text = model.year.toString()
        min_player.text = model.minplayer.toString()
        max_player.text = model.maxplayer.toString()
        min_age.text = model.minage.toString()
        primary_publisher.text = model.publisher
        rules_url.text = model.rulesurl

        getArtists(model.artists!!)

        primary_publisher.setOnClickListener{
            val myIntent = Intent(this, GameListActivity::class.java)
            myIntent.putExtra(PUBLISHER,primary_publisher.text!!)
            startActivity(myIntent)
        }

        rules_url.setOnClickListener {
            val url = Uri.parse(model.rulesurl)
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }

        img.setOnClickListener{
            if(model.url != null){
                val url = Uri.parse(model.url)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }else
                Toast.makeText(this,R.string.img_error_url, Toast.LENGTH_LONG).show()
        }


    }

    fun getArtists(artists : List<Artist>){
        artists.forEach {
            val artist = TextView(this)
            artist.text = it.artistName
            artist.setOnClickListener {
                val myIntent = Intent(this, GameListActivity::class.java)
                myIntent.putExtra(ARTIST,artist.text!!)
                startActivity(myIntent)
            }
            val list = findViewById<LinearLayout>(R.id.artists_list)
            list.addView(artist)
        }
    }
}

