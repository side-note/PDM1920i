package pt.isel.pdm.li52d.g4.bgg.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import pt.isel.pdm.li52d.g4.bgg.ARTIST
import pt.isel.pdm.li52d.g4.bgg.GAME_NAME
import pt.isel.pdm.li52d.g4.bgg.PUBLISHER
import pt.isel.pdm.li52d.g4.bgg.R
import pt.isel.pdm.li52d.g4.bgg.dto.GameDto
import kotlinx.android.synthetic.main.detailed_info.*
import java.math.BigDecimal
import java.math.RoundingMode


class DetailedGameInfoActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_info)

        val gameDto : GameDto = intent.getParcelableExtra(GAME_NAME)!!
        val name: TextView =  findViewById(R.id.name_id)
        val description: TextView = findViewById(R.id.game_description)
        val rating: TextView = findViewById(R.id.detailed_rating)
        val img : ImageView = findViewById(R.id.game_image)
        /*
        * GameListActivity:
        *   Passar os intents e observer para a factory, para o caso de o ViewModel ja estar craido nao o recriar
        *
        * DetailedGameInfoActivity:
        *   Criar um ViewModel e fazer o intent para o caso de o ViewModel ja estar criado nao o refazer (similar à nota acima)
        * */

        Glide.with(this)
            .load(gameDto.images!!.small)
            .into(game_image)
        description.text = gameDto.description
        description.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        name.text = gameDto.name
        rating.text =  " " + BigDecimal(gameDto.avgUserRating.toString()).setScale(1, RoundingMode.HALF_UP).toDouble().toString() + "/5"
        year_published.text = gameDto.yearPublished.toString()
        min_player.text = gameDto.minPlayer.toString()
        max_player.text = gameDto.maxPlayer.toString()
        min_age.text = gameDto.minAge.toString()
        primary_publisher.text = gameDto.primaryPublisher
        rules_url.text = gameDto.rulesUrl

        getArtists(gameDto.artists!!)

        primary_publisher.setOnClickListener{
            val myIntent = Intent(this, GameListActivity::class.java)
            myIntent.putExtra(PUBLISHER,primary_publisher.text!!)
            startActivity(myIntent)
        }

        rules_url.setOnClickListener {
            val url = Uri.parse(gameDto.rulesUrl)
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }

        img.setOnClickListener{
            if(gameDto.url != null){
                val url = Uri.parse(gameDto.url)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }else
                Toast.makeText(this,R.string.img_error_url, Toast.LENGTH_LONG).show()
        }


    }

    fun getArtists(artists : Array<String>){
        artists.forEach {
            val artist = TextView(this)
            artist.text = it
            artist.setOnClickListener {
                val myIntent = Intent(this, GameListActivity::class.java)
                myIntent.putExtra(ARTIST,artist.text!!)
                startActivity(myIntent)}

            val list = findViewById<LinearLayout>(R.id.artists_list)
            list.addView(artist)
        }
    }

}

