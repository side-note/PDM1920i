
package g5.li22d.poo.isel.pt.bgg.view

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Bundle
import android.text.Layout
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import g5.li22d.poo.isel.pt.bgg.GAME_NAME
import g5.li22d.poo.isel.pt.bgg.PUBLISHER
import g5.li22d.poo.isel.pt.bgg.R
import g5.li22d.poo.isel.pt.bgg.dto.GameDto
import kotlinx.android.synthetic.main.detailed_info.*
import java.math.BigDecimal
import java.math.RoundingMode


class DetailedGameInfoActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_info)

        val gameDto : GameDto = intent.getParcelableExtra(GAME_NAME)
        val name: TextView =  findViewById(R.id.name_id)
        val description: TextView = findViewById(R.id.game_description)
        val rating: TextView = findViewById(R.id.detailed_rating)

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

        var publishersString = ""
        for(s in gameDto.publishers!!){
            publishersString += (s + "\n")
        }

        publishers.text = publishersString



        primary_publisher.setOnClickListener{
            val myIntent = Intent(this, GameListActivity::class.java)
            myIntent.putExtra(PUBLISHER,primary_publisher.text!!)
            startActivity(myIntent)
        }


    }

}

