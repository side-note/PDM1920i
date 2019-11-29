package pt.isel.pdm.li52d.g4.bgg.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detailed_info.*
import pt.isel.pdm.li52d.g4.bgg.*
import pt.isel.pdm.li52d.g4.bgg.model.Artist
import pt.isel.pdm.li52d.g4.bgg.model.ArtistsAndGames
import java.math.BigDecimal
import java.math.RoundingMode


class DetailedGameInfoActivity() : AppCompatActivity(){

    val model : DetailedGameInfoViewModel by lazy {
        val factory = BGGViewModelFactoryProvider(intent)
        ViewModelProviders.of(this, factory)[DetailedGameInfoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_info)
        findViewById<ImageView>(R.id.add).setOnClickListener{
            val intent = Intent(this, ListsActivity::class.java)
            intent.putExtra(FROM_DETAILED_ACTIVITY, model.gameAndArtist)
            intent.putExtra(ILIST, IntentFromDetailed(true))
            startActivity(intent)
        }
//        findViewById<ImageView>(R.id.deleteGame).setOnClickListener{
//            val intent = Intent(this, ListsActivity::class.java)
//            intent.putExtra(FROM_DETAILED_ACTIVITY, model.gameAndArtist)
//            intent.putExtra(ILIST, IntentFromDetailed(false))
//            startActivity(intent)
//        }


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

        getArtists(model.artists)

        primary_publisher.setOnClickListener{
            PAGEACTIVITY = 1
            PAGEMODEL = 0
            SKIP = 0
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
                PAGEACTIVITY = 1
                PAGEMODEL = 0
                SKIP = 0
                val myIntent = Intent(this, GameListActivity::class.java)
                myIntent.putExtra(ARTIST,artist.text!!)
                startActivity(myIntent)
            }
            val list = findViewById<LinearLayout>(R.id.artists_list)
            list.addView(artist)
        }
    }
}

/**
 * Now this class has an boolean in the constructor just to know
 * if it is suppose to insert or delete from the database
 * */
class IntentFromDetailed(val insert: Boolean) : IListSelect{

    override var ctx: Context? = null
    override var act: Activity? = null
    override var artistsAndGames: ArtistsAndGames? = null

    override fun selectList(listName: String) {
        artistsAndGames!!.game.nameList = listName
        if(insert) {
            BggApp.CUSTOM_LIST_REPO.insertGameinList(artistsAndGames!!.game)
            artistsAndGames!!.artistList.forEach {
                BggApp.CUSTOM_LIST_REPO.insertArtist(listName, artistsAndGames!!.game.name, it.artistName)
            }
            act!!.onBackPressed()
//        }else{
//            BggApp.CUSTOM_LIST_REPO.deleteGamesinList(artistsAndGames!!.game)
//            act!!.startActivity(Intent(ctx, MainActivity::class.java))
        }
    }


    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as Boolean
    )

    override fun writeToParcel(dest: Parcel, flags: Int){ dest.writeValue(insert)}
    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<IntentFromDetailed> {
        override fun createFromParcel(parcel: Parcel): IntentFromDetailed = IntentFromDetailed(parcel)
        override fun newArray(size: Int): Array<IntentFromDetailed?> = arrayOfNulls(size)
    }

}

